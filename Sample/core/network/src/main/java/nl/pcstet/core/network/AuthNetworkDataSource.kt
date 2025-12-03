package nl.pcstet.core.network

import nl.pcstet.core.Error
import nl.pcstet.core.Result
import nl.pcstet.core.network.model.LoginRequestDto
import nl.pcstet.core.network.model.LoginResponseDto
import nl.pcstet.core.network.model.TestResponseDto

interface AuthNetworkDataSource {
    suspend fun test(apiUrl: String): Result<TestResponseDto, Error.Data.Network>
    suspend fun login(apiUrl: String, loginRequestDto: LoginRequestDto): Result<LoginResponseDto, Error.Data.Network>
    suspend fun authenticate(apiUrl: String, accessToken: String): Result<LoginResponseDto, Error.Data.Network>
}