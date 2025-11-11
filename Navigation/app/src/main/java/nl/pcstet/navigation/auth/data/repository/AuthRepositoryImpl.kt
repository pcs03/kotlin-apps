package nl.pcstet.navigation.auth.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import nl.pcstet.navigation.auth.data.network.ApiService
import nl.pcstet.navigation.auth.data.network.model.LoginRequestDto
import nl.pcstet.navigation.core.data.local.UserPreferencesDataStore
import nl.pcstet.navigation.core.data.utils.DataState
import nl.pcstet.navigation.core.data.utils.toApiException

class AuthRepositoryImpl(
    private val apiService: ApiService,
    private val userPreferencesDataStore: UserPreferencesDataStore,
) : AuthRepository {
    private val accessToken: Flow<String?> = userPreferencesDataStore.authToken
    override val isAuthenticated: Flow<Boolean> = accessToken.map { value ->
        if (!value.isNullOrBlank()) {
            authenticate(value)
        } else {
            false
        }
//
//
//        authenticate(value)

//        // If token exists, emit true
//        // TODO: authenticate with client
//        !value.isNullOrBlank()
    }

    override suspend fun login(
        email: String,
        password: String,
    ): Result<Unit> {
        val result = apiService.login(
            LoginRequestDto(
                username = email,
                password = password
            )
        )

        return if (result is DataState.Success) {
            val token = result.data.accessToken
            userPreferencesDataStore.saveToken(token)
            Result.success(Unit)
        } else {
            Result.failure(result.cause?.toApiException() ?: Exception("Login failed"))
        }
    }

    override suspend fun logout() {
        userPreferencesDataStore.clearToken()
    }

    suspend fun authenticate(token: String): Boolean {
        val result = apiService.authenticate(accessToken = token)
        return result is DataState.Success
    }
}
