package nl.pcstet.navigation.auth.data.network

import nl.pcstet.navigation.auth.data.network.model.AuthenticateResponseDto
import nl.pcstet.navigation.auth.data.network.model.LoginRequestDto
import nl.pcstet.navigation.auth.data.network.model.LoginResponseDto
import nl.pcstet.navigation.core.data.utils.ApiResult

interface AuthApiService {
    suspend fun login(loginRequestDto: LoginRequestDto): ApiResult<LoginResponseDto>
    suspend fun authenticate(accessToken: String): ApiResult<AuthenticateResponseDto>
}