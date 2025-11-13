package nl.pcstet.navigation.core.data.network

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import okio.withLock
import java.util.concurrent.locks.ReentrantLock

class ApiClientHolder(private val httpClientEngine: HttpClientEngine) {
    private var client: HttpClient? = null
    private var currentUrl: String? = null
    private val lock = ReentrantLock()

    fun getClient(): HttpClient {
        return lock.withLock {
            client ?: throw IllegalStateException("ApiClientHolder has not been initialized.")
        }
    }

    fun initialize(baseUrl: String? = null, accessToken: String? = null) {
        lock.withLock {
            if (currentUrl == baseUrl && client != null) {
                return
            }
            client?.close()

            client = HttpClient(httpClientEngine) {
                expectSuccess = true

                defaultRequest {
                    baseUrl?.let { url(baseUrl) }
                    contentType(ContentType.Application.Json)
                }

                // JSON Serialization
                install(ContentNegotiation) {
                    json(
                        Json {
                            ignoreUnknownKeys = true
                            prettyPrint = true
                            isLenient = true
                        }
                    )
                }

                // HTTP Timeout timer
                install(HttpTimeout) {
                    val timeoutDuration: Long = 30_000
                    requestTimeoutMillis = timeoutDuration
                    connectTimeoutMillis = timeoutDuration
                    socketTimeoutMillis = timeoutDuration
                }

                // Request Logging
                install(Logging) {
                    logger = object : Logger {
                        override fun log(message: String) {
                            Log.v("KTOR-LOG: ", message)
                        }
                    }
                    level = LogLevel.ALL
                }

                accessToken?.let {
                    install(Auth) {
                        bearer {
                            loadTokens {
                                BearerTokens(accessToken, "")
                            }
                        }
                    }
                }
            }
        }
    }
}