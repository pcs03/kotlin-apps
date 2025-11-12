package nl.pcstet.navigation.auth.di

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.URLProtocol
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import nl.pcstet.navigation.auth.data.network.ApiService
import nl.pcstet.navigation.auth.data.network.DummyJsonApiService
import nl.pcstet.navigation.auth.data.repository.AuthRepository
import nl.pcstet.navigation.auth.data.repository.AuthRepositoryImpl
import nl.pcstet.navigation.auth.presentation.login.LoginViewModel
import nl.pcstet.navigation.core.data.utils.HttpExceptions
import nl.pcstet.navigation.core.data.utils.toApiException
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

//fun provideHttpClient(engine: )

fun provideHttpClient(engine: HttpClientEngine) = HttpClient(engine) {
    val HARDCODED_API_HOST = "dummyjson.com"

    expectSuccess = true

    defaultRequest {
        url {
            protocol = URLProtocol.HTTPS
            host = HARDCODED_API_HOST
        }
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

    // Handle exceptions and HTTP errors
//    HttpResponseValidator {
//        handleResponseExceptionWithRequest { cause, _ ->
//            (cause as? Exception)?.toApiException()?.let {
//                throw it
//            }
//        }
//        validateResponse { response ->
//            if (!response.status.isSuccess()) {
//                val failureReason = when (response.status) {
//                    HttpStatusCode.Unauthorized -> "Unauthorized request"
//                    HttpStatusCode.Forbidden -> "${response.status.value} Missing API key"
//                    HttpStatusCode.NotFound -> "Invalid request"
//                    HttpStatusCode.RequestTimeout -> "Network timeout"
//                    in HttpStatusCode.InternalServerError..HttpStatusCode.GatewayTimeout -> "Server error"
//                    else -> "Network error"
//                }
//
//                throw HttpExceptions(
//                    response = response,
//                    failureReason = failureReason,
//                    cachedResponseText = response.bodyAsText()
//                )
//            }
//        }
//    }
}

val authModule = module {
    single<HttpClientEngine> { OkHttp.create() }
    single<HttpClient>(named("auth")) { provideHttpClient(get()) }

    single<ApiService> { DummyJsonApiService(get(named("auth"))) }
    singleOf(::AuthRepositoryImpl) { bind<AuthRepository>() }
    viewModelOf(::LoginViewModel)
}