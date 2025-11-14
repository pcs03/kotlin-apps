package nl.pcstet.navigation.main.data.network

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
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
    private var unauthenticatedClient: HttpClient? = null
    private var authenticatedClient: HttpClient? = null

    private var currentBaseUrl: String? = null
    private var currentAccessToken: String? = null

    private val lock = ReentrantLock()

    fun getAuthenticatedClient(): HttpClient {
        return lock.withLock {
            authenticatedClient
                ?: throw IllegalStateException("Authenticated API client has not been initialized.")
        }
    }

    fun initializeAuthenticatedClient(baseUrl: String, accessToken: String) {
        lock.withLock {
            if (currentBaseUrl == baseUrl && currentAccessToken == accessToken && authenticatedClient != null) {
                return
            }

            authenticatedClient?.close()

            authenticatedClient = createClient(baseUrl = baseUrl, accessToken = accessToken)
            currentBaseUrl = baseUrl
            currentAccessToken = accessToken

        }
    }

    fun getUnauthenticatedClient(): HttpClient {
        return lock.withLock {
            unauthenticatedClient
                ?: throw IllegalStateException("Authenticated API client has not been initialized.")
        }
    }

    fun initializeUnauthenticatedClient(baseUrl: String) {
        lock.withLock {
            if (currentBaseUrl == baseUrl && unauthenticatedClient != null) {
                return
            }

            unauthenticatedClient?.close()

            unauthenticatedClient = createClient(baseUrl = baseUrl)
            currentBaseUrl = baseUrl

        }
    }

    private fun createClient(baseUrl: String, accessToken: String? = null): HttpClient {
        return HttpClient(httpClientEngine) {
            expectSuccess = true

            defaultRequest {
                url(baseUrl)
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