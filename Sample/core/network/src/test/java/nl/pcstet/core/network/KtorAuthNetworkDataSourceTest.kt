package nl.pcstet.core.network

import android.util.Log
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeTypeOf
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.request.request
import io.ktor.http.HttpStatusCode
import io.ktor.http.headers
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import nl.pcstet.core.Result
import nl.pcstet.core.network.data.LoginResponses
import nl.pcstet.core.network.data.TestResponses
import nl.pcstet.core.network.model.LoginRequestDto
import nl.pcstet.core.network.model.LoginResponseDto
import nl.pcstet.core.network.utils.HttpClientFactory
import nl.pcstet.core.network.utils.httpResponseHeaders
import kotlin.test.BeforeTest
import kotlin.test.Test

class KtorAuthNetworkDataSourceSuccessResponseTest {
    private lateinit var httpClient: HttpClient
    @BeforeTest
    fun setUp() {
        val mockHttpEngine = MockEngine.create {
            addHandler { request ->
                val path = request.url.encodedPath
                when(path) {
                    "/auth/login" -> respond(
                        status = HttpStatusCode.OK,
                        content = LoginResponses.valid,
                        headers = httpResponseHeaders
                    )
                    "/auth/me" -> respond(
                        status = HttpStatusCode.OK,
                        content = TestResponses.valid,
                        headers = httpResponseHeaders
                    )
                    else -> respond(
                        status = HttpStatusCode.NotFound,
                        content = "Not found"
                    )
                }
            }
        }
        httpClient = HttpClientFactory.create(mockHttpEngine)
    }

    @Test
    fun `should return LoginResponseDto when API call is success`() = runTest {

    }
}

class KtorAuthNetworkDataSourceTest {
    private val mockHttpClient = HttpClientFactory.create(
        MockEngine.create {
            addHandler { request ->
                println(request.url.encodedPath)
                when (request.url.encodedPath) {
                    "/auth/login" -> respond(
                        status = HttpStatusCode.OK,
                        content = LoginResponses.valid,
                        headers = httpResponseHeaders
                    )

                    "/auth/test" -> respond(
                        status = HttpStatusCode.OK,
                        content = TestResponses.valid,
                        headers = httpResponseHeaders
                    )
//                "/auth/me" -> respond(
//                    status = HttpStatusCode.OK,
//                    content = Authen.valid,
//                    headers = httpResponseHeaders
//                )
                    else -> respond(
                        status = HttpStatusCode.NotFound,
                        content = "Not found"
                    )
                }
            }
        }
    )

    private val mockKtorAuthNetworkDataSource = KtorAuthNetworkDataSource(mockHttpClient)

    @Test
    fun `should return LoginResponseDto when API call successful`() = runTest {
        val result = mockKtorAuthNetworkDataSource.login(
            apiUrl = "https://example.com",
            loginRequestDto = LoginRequestDto(
                username = "emilys",
                password = "emilyspass"
            )
        )

        result.shouldBeTypeOf<Result.Success<LoginResponseDto>>()
    }

}