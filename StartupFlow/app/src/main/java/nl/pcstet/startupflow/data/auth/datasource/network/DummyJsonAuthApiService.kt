package nl.pcstet.startupflow.data.auth.datasource.network

import io.ktor.client.HttpClient
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.setBody
import io.ktor.http.HttpMethod
import nl.pcstet.startupflow.data.auth.datasource.network.model.AuthenticateResponseDto
import nl.pcstet.startupflow.data.auth.datasource.network.model.LoginRequestDto
import nl.pcstet.startupflow.data.auth.datasource.network.model.LoginResponseDto
import nl.pcstet.startupflow.data.auth.datasource.network.model.TestResponseDto
import nl.pcstet.startupflow.data.core.datasource.network.utils.ApiResult
import nl.pcstet.startupflow.data.core.datasource.network.utils.safeRequest

class DummyJsonAuthApiService(
    private val httpClient: HttpClient,
) : AuthApiService {
    override suspend fun test(apiUrl: String): ApiResult<TestResponseDto> {
        return httpClient.safeRequest("$apiUrl/test") {
            method = HttpMethod.Get
        }
    }

    override suspend fun login(
        apiUrl: String,
        loginRequestDto: LoginRequestDto,
    ): ApiResult<LoginResponseDto> {
        return httpClient.safeRequest<LoginResponseDto>("$apiUrl/auth/login") {
            method = HttpMethod.Post
            setBody(loginRequestDto)
        }
    }

    override suspend fun authenticate(
        apiUrl: String,
        accessToken: String,
    ): ApiResult<AuthenticateResponseDto> {
        return httpClient.safeRequest<AuthenticateResponseDto>("$apiUrl/auth/me") {
            method = HttpMethod.Get
            bearerAuth(accessToken)
        }
    }
}