package nl.pcstet.navigation.auth.data.repository

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import nl.pcstet.navigation.auth.data.network.ApiService
import nl.pcstet.navigation.auth.data.network.model.LoginRequestDto
import nl.pcstet.navigation.main.data.local.UserPreferencesDataStore
import nl.pcstet.navigation.core.data.utils.ApiResult
import nl.pcstet.navigation.core.data.utils.AuthState

class AuthRepositoryImpl(
    private val apiService: ApiService,
    private val userPreferencesDataStore: UserPreferencesDataStore,
    private val coroutineScope: CoroutineScope
) : AuthRepository {
    private val _authState = MutableStateFlow<AuthState>(AuthState.Unknown)
    override val authState: StateFlow<AuthState> = _authState.asStateFlow()
    private var validatedToken: String? = null

    init {
        coroutineScope.launch {
            authState.collect { authState ->
                Log.d("AuthRepository", authState.toString())
            }
        }
        coroutineScope.launch {
            userPreferencesDataStore.authToken.collect { accessToken ->
                Log.d("AuthRepository", accessToken.toString())
                if (accessToken.isNullOrBlank()) {
                    _authState.value = AuthState.InvalidToken
                    validatedToken = null
                } else if (accessToken == validatedToken) {
                    if (_authState.value !is AuthState.Authenticated) {
                        _authState.value = AuthState.Authenticated(accessToken = accessToken)
                    }
                } else {
                    authenticate(accessToken)
                }
            }
        }
    }


    override suspend fun login(
        email: String,
        password: String,
    ) {
        val result = apiService.login(
            LoginRequestDto(
                username = email,
                password = password
            )
        )

        return when (result) {
            is ApiResult.Success -> {
                val token = result.data.accessToken
                validatedToken = token
                userPreferencesDataStore.saveAuthToken(token)
                _authState.value = AuthState.Authenticated(accessToken = token)
            }

            is ApiResult.Failure -> {
                val httpCode = result.cause.statusCode
                val errorState = when (httpCode) {
                    400 -> AuthState.Unauthenticated(message = "Invalid credentials")
                    else -> AuthState.Unauthenticated(message = result.cause.message.toString())
                }
                _authState.value = errorState
            }
        }
    }

    override suspend fun logout() {
        validatedToken = null
        userPreferencesDataStore.clearAuthToken()
        _authState.value = AuthState.InvalidToken
    }

    suspend fun authenticate(token: String) {
        val result = apiService.authenticate(accessToken = token)

        when (result) {
            is ApiResult.Success -> {
                validatedToken = token
                _authState.value = AuthState.Authenticated(accessToken = token)
            }

            is ApiResult.Failure -> {
                validatedToken = null
                _authState.value = AuthState.InvalidToken
            }
        }
    }
}
