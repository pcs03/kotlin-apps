package nl.pcstet.navigation.onboarding.network

import io.ktor.http.HttpMethod
import io.ktor.http.path
import kotlinx.serialization.Serializable
import nl.pcstet.navigation.main.data.network.ApiClientHolder
import nl.pcstet.navigation.core.data.utils.ApiResult
import nl.pcstet.navigation.core.data.utils.safeRequest

@Serializable
data class TestDto (
    val status: String
)

class OnboardingApiService(
    private val apiClientHolder: ApiClientHolder
) {
    suspend fun test(): ApiResult<TestDto> {
        val client = apiClientHolder.getUnauthenticatedClient()
        return client.safeRequest<TestDto> {
            url {
                path("test")
            }
            method = HttpMethod.Get
        }
    }
}
