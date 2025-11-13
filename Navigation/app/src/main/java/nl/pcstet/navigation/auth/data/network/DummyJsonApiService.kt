package nl.pcstet.navigation.auth.data.network

import io.ktor.client.HttpClient
import io.ktor.client.request.header
import io.ktor.client.request.setBody
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.path
import kotlinx.coroutines.delay
import nl.pcstet.navigation.auth.data.network.model.AuthenticateResponseDto
import nl.pcstet.navigation.auth.data.network.model.LoginRequestDto
import nl.pcstet.navigation.auth.data.network.model.LoginResponseDto
import nl.pcstet.navigation.core.data.network.ApiClientHolder
import nl.pcstet.navigation.core.data.utils.ApiResult
import nl.pcstet.navigation.core.data.utils.DataState
import nl.pcstet.navigation.core.data.utils.safeRequest
import nl.pcstet.navigation.core.data.utils.toApiException

class DummyJsonApiService(
    private val apiClientHolder: ApiClientHolder,
) : ApiService {
    override suspend fun login(loginRequestDto: LoginRequestDto): ApiResult<LoginResponseDto> {
        val client = apiClientHolder.getClient()
        return client.safeRequest<LoginResponseDto> {
            url {
                path("auth/login")
            }
            method = HttpMethod.Post
            setBody(loginRequestDto)
        }
    }

    override suspend fun authenticate(accessToken: String): ApiResult<AuthenticateResponseDto> {
        val client = apiClientHolder.getClient()
        return client.safeRequest<AuthenticateResponseDto> {
            url {
                path("auth/me")
            }
            method = HttpMethod.Get
            header(HttpHeaders.Authorization, "Bearer $accessToken")
        }
    }


}