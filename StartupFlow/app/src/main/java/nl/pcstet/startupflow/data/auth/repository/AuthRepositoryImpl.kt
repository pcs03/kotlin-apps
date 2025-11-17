package nl.pcstet.startupflow.data.auth.repository

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import nl.pcstet.startupflow.data.auth.datasource.network.AuthApiService
import nl.pcstet.startupflow.data.auth.repository.model.AuthState
import nl.pcstet.startupflow.data.auth.repository.model.LoginCredentials
import nl.pcstet.startupflow.data.auth.repository.model.toLoginRequestDto
import nl.pcstet.startupflow.data.core.datasource.disk.SettingsDataStore
import nl.pcstet.startupflow.data.core.datasource.network.utils.ApiException
import nl.pcstet.startupflow.data.core.datasource.network.utils.ApiResult
import nl.pcstet.startupflow.data.utils.DataState
import nl.pcstet.startupflow.data.core.datasource.network.utils.onSuccess
import nl.pcstet.startupflow.data.core.datasource.network.utils.toDataState
import nl.pcstet.startupflow.data.core.manager.dispatcher.DispatcherManager
import nl.pcstet.startupflow.data.utils.map

class AuthRepositoryImpl(
    private val settingsDataStore: SettingsDataStore,
    private val authApiService: AuthApiService,
    dispatcherManager: DispatcherManager,
) : AuthRepository {
    private val unconfinedScope = CoroutineScope(dispatcherManager.unconfined)
    private val ioScope = CoroutineScope(dispatcherManager.io)

    override val authStateFlow: StateFlow<AuthState> = combine(
        settingsDataStore.apiUrl,
        settingsDataStore.accessToken
    ) { apiUrl, accessToken ->
        getAuthState(apiUrl, accessToken)
    }.stateIn(
        scope = ioScope,
        started = SharingStarted.Eagerly,
        initialValue = AuthState.Loading
    )

    private suspend fun getAuthState(apiUrl: String?, accessToken: String?): AuthState {
        val apiUrl = settingsDataStore.apiUrl.first()
        if (apiUrl.isNullOrEmpty()) {
            return AuthState.OnboardingRequired
        }

        val accessToken = settingsDataStore.accessToken.first()
        if (accessToken.isNullOrEmpty()) {
            return AuthState.Unauthenticated
        }

        val result = authApiService.authenticate(apiUrl, accessToken)
        return when (result) {
            is ApiResult.Success -> AuthState.Authenticated
            is ApiResult.Failure -> AuthState.Unauthenticated
        }
    }

    override suspend fun getAuthState(): AuthState {
        val apiUrl = settingsDataStore.apiUrl.first()
        if (apiUrl.isNullOrEmpty()) {
            return AuthState.OnboardingRequired
        }

        val accessToken = settingsDataStore.accessToken.first()
        if (accessToken.isNullOrEmpty()) {
            return AuthState.Unauthenticated
        }

        val result = authApiService.authenticate(apiUrl, accessToken)
        return when (result) {
            is ApiResult.Success -> AuthState.Authenticated
            is ApiResult.Failure -> AuthState.Unauthenticated
        }
    }

    override suspend fun login(credentials: LoginCredentials): DataState<String> {
        val apiUrl = settingsDataStore.apiUrl.first()
        if (apiUrl.isNullOrEmpty()) {
            return DataState.Error(ApiException.Generic("Illegal state. Login attempted, but no API URL found."))
        }

        val result = authApiService.login(apiUrl, credentials.toLoginRequestDto())
        return result
            .onSuccess { data -> settingsDataStore.saveAccessToken(data.accessToken) }
            .toDataState().map { loginResponseDto -> loginResponseDto.accessToken }
    }

    override suspend fun clearAccessToken() {
        settingsDataStore.clearAccessToken()
    }
}