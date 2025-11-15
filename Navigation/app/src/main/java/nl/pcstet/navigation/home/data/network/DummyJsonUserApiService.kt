package nl.pcstet.navigation.home.data.network

import io.ktor.http.HttpMethod
import io.ktor.http.path
import nl.pcstet.navigation.core.data.utils.ApiResult
import nl.pcstet.navigation.core.data.utils.safeRequest
import nl.pcstet.navigation.home.data.network.model.UserDto
import nl.pcstet.navigation.main.data.network.ApiClientHolder

class DummyJsonUserApiService(
    private val apiClientHolder: ApiClientHolder,
) : UserApiService {
    val client = apiClientHolder.getAuthenticatedClient()

    override suspend fun getUser(): ApiResult<UserDto> {
        return client.safeRequest<UserDto> {
            url {
                path("user/me")
            }
            method = HttpMethod.Get
        }
    }
}