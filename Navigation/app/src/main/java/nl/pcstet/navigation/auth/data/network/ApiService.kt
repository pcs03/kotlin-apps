package nl.pcstet.navigation.auth.data.network

import nl.pcstet.navigation.auth.data.network.model.AuthenticateResponseDto
import nl.pcstet.navigation.auth.data.network.model.LoginRequestDto
import nl.pcstet.navigation.auth.data.network.model.LoginResponseDto
import nl.pcstet.navigation.core.data.utils.DataState

interface ApiService {
    suspend fun login(loginRequestDto: LoginRequestDto): DataState<LoginResponseDto>
    suspend fun authenticate(accessToken: String): DataState<AuthenticateResponseDto>
}