package nl.pcstet.core.network.utils

import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.MockRequestHandler
import io.ktor.client.engine.mock.respond
import io.ktor.http.ContentType
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import kotlinx.serialization.json.Json

internal val httpResponseHeaders =
    headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString())

internal fun createMockHttpClient(handler: MockRequestHandler): HttpClient {
    val mockEngine = MockEngine.create {
        addHandler(handler)
    }
    return HttpClientFactory.create(mockEngine)
}

internal inline fun <reified T> createMockRequestHandler(
    content: T,
    responseStatusCode: HttpStatusCode = HttpStatusCode.OK,
    responseHeaders: Headers = httpResponseHeaders,
    exception: Exception? = null,
): MockRequestHandler {
    return { request ->
        exception?.let {
            throw exception
        }
        respond(
            content = Json.encodeToString<T>(content),
            status = responseStatusCode,
            headers = responseHeaders,
        )
    }
}
