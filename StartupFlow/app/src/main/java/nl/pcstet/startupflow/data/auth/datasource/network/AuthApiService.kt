package nl.pcstet.startupflow.data.auth.datasource.network

import nl.pcstet.startupflow.data.auth.datasource.network.model.AuthenticateResponseDto
import nl.pcstet.startupflow.data.auth.datasource.network.model.LoginRequestDto
import nl.pcstet.startupflow.data.auth.datasource.network.model.LoginResponseDto
import nl.pcstet.startupflow.data.auth.datasource.network.model.TestResponseDto
import nl.pcstet.startupflow.data.core.datasource.network.utils.ApiResult

interface AuthApiService {
    suspend fun test(apiUrl: String): ApiResult<TestResponseDto>
    suspend fun login(apiUrl: String, loginRequestDto: LoginRequestDto): ApiResult<LoginResponseDto>
    suspend fun authenticate(apiUrl: String, accessToken: String): ApiResult<AuthenticateResponseDto>
}