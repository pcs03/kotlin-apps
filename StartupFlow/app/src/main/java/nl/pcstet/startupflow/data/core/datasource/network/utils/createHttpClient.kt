package nl.pcstet.startupflow.data.core.datasource.network.utils

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

fun createHttpClient(engine: HttpClientEngine): HttpClient {
    return HttpClient(engine) {
        expectSuccess = true

        defaultRequest {
            contentType(ContentType.Application.Json)
        }

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
    }
}