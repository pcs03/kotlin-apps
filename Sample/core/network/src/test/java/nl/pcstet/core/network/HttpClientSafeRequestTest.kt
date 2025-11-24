package nl.pcstet.core.network

import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import io.kotest.matchers.types.shouldBeTypeOf
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.MockRequestHandler
import io.ktor.client.engine.mock.respond
import io.ktor.http.ContentType
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import nl.pcstet.core.Error
import nl.pcstet.core.Result
import nl.pcstet.core.network.utils.HttpClientFactory
import nl.pcstet.core.network.utils.safeRequest
import java.io.IOException
import kotlin.test.Test

class HttpClientSafeRequestTest {
    data class HttpGetWithResultSuccessTestData(
        val statusCode: HttpStatusCode,
        val responseContent: SampleDto = SampleDto(),
    ) {
        val expectedResult = Result.Success(responseContent)
    }

    val httpGetWithResultSuccessTests = listOf(
        HttpGetWithResultSuccessTestData(HttpStatusCode.OK, responseContent = SampleDto(1, "test"))
    )

    @Serializable
    data class SampleDto(val id: Int = 1, val name: String = "name")

    data class NonSerializableSampleDto(val id: Int, val name: String)

    private val sampleDto = SampleDto(id = 1, name = "test")
    private val nonSerializableSampleDto = NonSerializableSampleDto(id = 1, name = "test")

    private val httpResponseHeaders =
        headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString())

    private fun createMockHttpClient(handler: MockRequestHandler): HttpClient {
        val mockEngine = MockEngine.create {
            addHandler(handler)
        }
        return HttpClientFactory.create(mockEngine)
    }

    private inline fun <reified T> createMockRequestHandler(
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

    @Test
    fun `should return Result Success with Sample DTO when API response is 200 and valid JSON`() =
        runTest {
            val client = createMockHttpClient(
                createMockRequestHandler(
                    content = sampleDto, responseStatusCode = HttpStatusCode.OK
                )
            )

            val response = client.safeRequest<SampleDto>("https://example.com/test") {
                method = HttpMethod.Get
            }

            response.shouldBeTypeOf<Result.Success<*>>()
            response.data.shouldBeTypeOf<SampleDto>()

        }

    @Test
    fun `should return Result Error NO_INTERNET when IOException thrown`() = runTest {
        val client = createMockHttpClient(
            createMockRequestHandler(
                content = sampleDto,
                responseStatusCode = HttpStatusCode.OK,
                exception = IOException()
            )
        )

        val response = client.safeRequest<SampleDto>("https://example.com/test") {
            method = HttpMethod.Get
        }

        response.shouldBeTypeOf<Result.Error<*>>()
        response.error shouldBe Error.Data.Network.NO_INTERNET
    }

    @Test
    fun `should return Result Error SERIALIZATION when SerializationException thrown`() = runTest {
        val client = createMockHttpClient(
            createMockRequestHandler(
                content = nonSerializableSampleDto, responseStatusCode = HttpStatusCode.OK
            )
        )

        val response = client.safeRequest<NonSerializableSampleDto>("https://example.com/test") {
            method = HttpMethod.Get
        }

        response.shouldBeInstanceOf<Result.Error<*>>()
        response.error shouldBe Error.Data.Network.SERIALIZATION
    }

    @Test
    fun `should return Result Error Unauthorized when API response is 401`() = runTest {
        val client = createMockHttpClient(
            createMockRequestHandler(
                content = sampleDto, responseStatusCode = HttpStatusCode.Unauthorized
            )
        )

        val response = client.safeRequest<SampleDto>("https://example.com/test") {
            method = HttpMethod.Get
        }

        response.shouldBeInstanceOf<Result.Error<*>>()
        response.error shouldBe Error.Data.Network.UNAUTHORIZED
    }

    @Test
    fun `should return Result Error Not Found when API response is 404`() = runTest {
        val client = createMockHttpClient(
            createMockRequestHandler(
                content = sampleDto, responseStatusCode = HttpStatusCode.NotFound
            )
        )

        val response = client.safeRequest<SampleDto>("https://example.com/test") {
            method = HttpMethod.Get
        }

        response.shouldBeTypeOf<Result.Error<*>>()
        response.error shouldBe Error.Data.Network.NOT_FOUND
    }
}

