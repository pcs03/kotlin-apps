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

    private data class HttpTestCase<T>(
        val description: String,
        val responseContent: T? = null, // The object the mock engine returns
        val statusCode: HttpStatusCode = HttpStatusCode.OK,
        val exception: Exception? = null,
        // This captures the specific safeRequest<T> call with correct types
        val action: suspend (HttpClient) -> Result<T, Error.Data.Network>,
        val expectedError: Error.Data.Network? = null
    )

    private suspend inline fun <reified T> executeTestCase(testCase: HttpTestCase<T>) {
        val client = createMockHttpClient(
            createMockRequestHandler(
                content = sampleDto,
                responseStatusCode = testCase.statusCode,
                exception = testCase.exception,
            )
        )

        val result = testCase.action(client)
        if (testCase.expectedError == null) {
            result.shouldBeTypeOf<Result.Success<T>>()
        } else {
            result.shouldBeTypeOf<Result.Error<Error.Data.Network>>()
            result.error shouldBe testCase.expectedError
        }

    }

    @Test
    fun `should return 'Result Success' with content when API request is successful`() =
        runTest {
            val httpTestCases = listOf(
                HttpTestCase<SampleDto>(
                    description = "should return 'Result Success' with 'SampleDto' when API response is 200 and valid JSON",
                    responseContent = sampleDto,
                    statusCode = HttpStatusCode.OK,
                    exception = null,
                    action = { client ->
                        client.safeRequest("https://example.com/test") {
                            method = HttpMethod.Get
                        }
                    },
                    expectedError = null
                ),
            )

            httpTestCases.forEach { executeTestCase(it) }
        }


    @Test
    fun `should return 'Result Error' with correct 'Error Data Network' when API request return status code in 400 or 500 range`() =
        runTest {
            val httpTestCases = listOf(
                HttpTestCase<SampleDto>(
                    description = "should return 'Result Data Network Error' with 'BAD_REQUEST' when API response is 400 and valid JSON",
                    responseContent = sampleDto,
                    statusCode = HttpStatusCode.BadRequest,
                    exception = null,
                    action = { client ->
                        client.safeRequest("https://example.com/test") {
                            method = HttpMethod.Get
                        }
                    },
                    expectedError = Error.Data.Network.BAD_REQUEST
                ),
                HttpTestCase<SampleDto>(
                    description = "should return 'Result Data Network Error' with 'UNAUTHORIZED' when API response is 401 and valid JSON",
                    responseContent = sampleDto,
                    statusCode = HttpStatusCode.Unauthorized,
                    exception = null,
                    action = { client ->
                        client.safeRequest("https://example.com/test") {
                            method = HttpMethod.Get
                        }
                    },
                    expectedError = Error.Data.Network.UNAUTHORIZED
                ),
                HttpTestCase<SampleDto>(
                    description = "should return 'Result Data Network Error' with 'FORBIDDEN' when API response is 403 and valid JSON",
                    responseContent = sampleDto,
                    statusCode = HttpStatusCode.Forbidden,
                    exception = null,
                    action = { client ->
                        client.safeRequest("https://example.com/test") {
                            method = HttpMethod.Get
                        }
                    },
                    expectedError = Error.Data.Network.FORBIDDEN
                ),
                HttpTestCase<SampleDto>(
                    description = "should return 'Result Data Network Error' with 'NOT_FOUND' when API response is 404 and valid JSON",
                    responseContent = sampleDto,
                    statusCode = HttpStatusCode.NotFound,
                    exception = null,
                    action = { client ->
                        client.safeRequest("https://example.com/test") {
                            method = HttpMethod.Get
                        }
                    },
                    expectedError = Error.Data.Network.NOT_FOUND
                ),
                HttpTestCase<SampleDto>(
                    description = "should return 'Result Data Network Error' with 'REQUEST_TIMEOUT' when API response is 408 and valid JSON",
                    responseContent = sampleDto,
                    statusCode = HttpStatusCode.RequestTimeout,
                    exception = null,
                    action = { client ->
                        client.safeRequest("https://example.com/test") {
                            method = HttpMethod.Get
                        }
                    },
                    expectedError = Error.Data.Network.REQUEST_TIMEOUT
                ),
                HttpTestCase<SampleDto>(
                    description = "should return 'Result Data Network Error' with 'PAYLOAD_TOO_LARGE' when API response is 413 and valid JSON",
                    responseContent = sampleDto,
                    statusCode = HttpStatusCode.PayloadTooLarge,
                    exception = null,
                    action = { client ->
                        client.safeRequest("https://example.com/test") {
                            method = HttpMethod.Get
                        }
                    },
                    expectedError = Error.Data.Network.PAYLOAD_TOO_LARGE
                ),
                HttpTestCase<SampleDto>(
                    description = "should return 'Result Data Network Error' with 'TOO_MANY_REQUESTS' when API response is 429 and valid JSON",
                    responseContent = sampleDto,
                    statusCode = HttpStatusCode.TooManyRequests,
                    exception = null,
                    action = { client ->
                        client.safeRequest("https://example.com/test") {
                            method = HttpMethod.Get
                        }
                    },
                    expectedError = Error.Data.Network.TOO_MANY_REQUESTS
                ),
                HttpTestCase<SampleDto>(
                    description = "should return 'Result Data Network Error' with 'GENERIC_CLIENT' when API response is 411",
                    responseContent = sampleDto,
                    statusCode = HttpStatusCode.LengthRequired,
                    exception = null,
                    action = { client ->
                        client.safeRequest("https://example.com/test") {
                            method = HttpMethod.Get
                        }
                    },
                    expectedError = Error.Data.Network.GENERIC_CLIENT
                ),
                HttpTestCase<SampleDto>(
                    description = "should return 'Result Data Network Error' with 'SERVER_ERROR' when API response is 500",
                    responseContent = sampleDto,
                    statusCode = HttpStatusCode.InternalServerError,
                    exception = null,
                    action = { client ->
                        client.safeRequest("https://example.com/test") {
                            method = HttpMethod.Get
                        }
                    },
                    expectedError = Error.Data.Network.SERVER_ERROR
                ),
                HttpTestCase<SampleDto>(
                    description = "should return 'Result Data Network Error' with 'BAD_GATEWAY' when API response is 502",
                    responseContent = sampleDto,
                    statusCode = HttpStatusCode.BadGateway,
                    exception = null,
                    action = { client ->
                        client.safeRequest("https://example.com/test") {
                            method = HttpMethod.Get
                        }
                    },
                    expectedError = Error.Data.Network.BAD_GATEWAY
                ),
                HttpTestCase<SampleDto>(
                    description = "should return 'Result Data Network Error' with 'GENERIC_SERVER' when API response is 505",
                    responseContent = sampleDto,
                    statusCode = HttpStatusCode.VersionNotSupported,
                    exception = null,
                    action = { client ->
                        client.safeRequest("https://example.com/test") {
                            method = HttpMethod.Get
                        }
                    },
                    expectedError = Error.Data.Network.GENERIC_SERVER
                ),
            )

            httpTestCases.forEach { executeTestCase(it) }
        }


    @Test
    fun `should return 'Result Error' with correct 'Error Data Network' when API request throws exception`() =
        runTest {
            val httpTestCases = listOf(
                HttpTestCase<SampleDto>(
                    description = "should return 'Result Data Network Error' with 'NO_INTERNET' when request throws IOException",
                    responseContent = sampleDto,
                    statusCode = HttpStatusCode.OK,
                    exception = okio.IOException(),
                    action = { client ->
                        client.safeRequest("https://example.com/test") {
                            method = HttpMethod.Get
                        }
                    },
                    expectedError = Error.Data.Network.NO_INTERNET
                ),
            )

            httpTestCases.forEach { executeTestCase(it) }
        }
}

