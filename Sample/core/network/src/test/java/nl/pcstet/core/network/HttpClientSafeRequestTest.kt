package nl.pcstet.core.network

import assertk.assertThat
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.HttpResponseData
import io.ktor.client.request.get
import io.ktor.client.request.request
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import io.ktor.utils.io.ByteReadChannel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.Serializable
import nl.pcstet.core.Error
import nl.pcstet.core.Result
import nl.pcstet.core.network.di.networkTestModule
import nl.pcstet.core.network.utils.HttpClientFactory
import nl.pcstet.core.network.utils.safeRequest
import org.junit.Before
import org.junit.Rule
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.get
import kotlin.test.Test
import kotlin.test.assertEquals

@Serializable
data class IpResponse(val ip: String)

class ApiClient(engine: HttpClientEngine) {
    private val httpClient = HttpClient(engine) {
        install(ContentNegotiation) {
            json()
        }
    }

    suspend fun getIp(): IpResponse =
        httpClient.get("https://api.ipify.org/?format=json").body<IpResponse>()
}

class ApiClientTest {
//    @get:Rule
//    val koinTestRule = KoinTestRule.create {
//        modules(
//            networkTestModule
//        )
//    }

    private lateinit var httpClient: HttpClient

    @Before
    fun setUp() {
        val mockEngine = MockEngine.create {
            addHandler { request ->
                val relativeUrl = request.url.encodedPath
                when (relativeUrl) {
                    "/ip" -> respond(
                        content = ByteReadChannel("""{"ip":"127.0.0.1"}"""),
                        status = HttpStatusCode.OK,
                        headers = headersOf(HttpHeaders.ContentType, "application/json")
                    )

                    else -> respond(
                        content = "Not mocked",
                        status = HttpStatusCode.NotFound
                    )
                }
            }
        }
        httpClient = HttpClientFactory.create(mockEngine)
    }

    @Test
    fun testIpResponse() = runTest {

        val response = httpClient.safeRequest<IpResponse>("https://test.example.com/ip") {
            method = HttpMethod.Get
        }

        assertThat(response is Result.Success)
//        assertEquals("127.0.0.1", response.)
    }
}