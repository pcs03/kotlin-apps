package nl.pcstet.core.network

import io.ktor.client.HttpClient
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.setBody
import io.ktor.http.HttpMethod
import nl.pcstet.core.Error
import nl.pcstet.core.Result
import nl.pcstet.core.network.model.LoginRequestDto
import nl.pcstet.core.network.model.LoginResponseDto
import nl.pcstet.core.network.model.TestResponseDto
import nl.pcstet.core.network.utils.safeRequest

class KtorAuthNetworkDataSource(
    private val httpClient: HttpClient,
) : AuthNetworkDataSource {
    override suspend fun test(apiUrl: String): Result<TestResponseDto, Error.Data.Network> {
        return httpClient.safeRequest("$apiUrl/test") {
            method = HttpMethod.Get
        }
    }

    override suspend fun login(
        apiUrl: String,
        loginRequestDto: LoginRequestDto
    ): Result<LoginResponseDto, Error.Data.Network> {
        return httpClient.safeRequest("$apiUrl/auth/login") {
            method = HttpMethod.Post
            setBody(loginRequestDto)
        }
    }

    override suspend fun authenticate(
        apiUrl: String,
        accessToken: String
    ): Result<LoginResponseDto, Error.Data.Network> {
        return httpClient.safeRequest("$apiUrl/auth/me") {
            method = HttpMethod.Get
            bearerAuth(accessToken)
        }
    }
}