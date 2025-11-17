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
import nl.pcstet.startupflow.data.core.datasource.disk.SettingsDataSourceImpl
import nl.pcstet.startupflow.data.core.datasource.network.utils.ApiException
import nl.pcstet.startupflow.data.core.datasource.network.utils.onFailure
import nl.pcstet.startupflow.data.core.datasource.network.utils.onSuccess
import nl.pcstet.startupflow.data.core.datasource.network.utils.toDataState
import nl.pcstet.startupflow.data.core.manager.dispatcher.DispatcherManager
import nl.pcstet.startupflow.data.utils.DataState
import nl.pcstet.startupflow.data.utils.map

class AuthRepositoryImpl(
    private val settingsDataStore: SettingsDataSourceImpl,
    private val authApiService: AuthApiService,
    dispatcherManager: DispatcherManager,
) : AuthRepository {
    private val unconfinedScope = CoroutineScope(dispatcherManager.unconfined)
    private val ioScope = CoroutineScope(dispatcherManager.io)

    // TODO: There is currently no handling for an expired access token
    override val authState: StateFlow<AuthState> = combine(
        settingsDataStore.apiUrl,
        settingsDataStore.accessToken,
        settingsDataStore.hasUserLoggedInOrCreatedAccount,
        settingsDataStore.isOnboardingComplete,
        settingsDataStore.emailAddress,
    ) { apiUrl, accessToken, hasUserLoggedInOrCreatedAccount, isOnboardingComplete, emailAddress ->
        if (apiUrl.isNullOrEmpty()) {
            return@combine AuthState.Unauthenticated
        }
        if (accessToken.isNullOrEmpty()) {
            return@combine AuthState.Locked(apiUrl = apiUrl, emailAddress = emailAddress)

        }

        when (isOnboardingComplete) {
            true -> AuthState.Unlocked(apiUrl = apiUrl, accessToken = accessToken)
            false -> AuthState.Unauthenticated
        }
    }.stateIn(
        scope = unconfinedScope,
        started = SharingStarted.Eagerly,
        initialValue = AuthState.Loading
    )

    override suspend fun checkAccessTokenValid(): DataState<Any> {
        val apiUrl = settingsDataStore.apiUrl.first() ?: throw IllegalStateException("API URL not found.")
        val accessToken = settingsDataStore.accessToken.first() ?: throw IllegalStateException("Must be logged in.")
        val result = authApiService.authenticate(apiUrl, accessToken)

        return result
            .onFailure { settingsDataStore.clearAccessToken() }
            .toDataState()
    }

    override suspend fun login(credentials: LoginCredentials): DataState<String> {
        val apiUrl = settingsDataStore.apiUrl.first()
        if (apiUrl.isNullOrEmpty()) {
            return DataState.Error(ApiException.Generic("Illegal state. Login attempted, but no API URL found."))
        }

        val result = authApiService.login(apiUrl, credentials.toLoginRequestDto())
        return result
            .onSuccess { data -> settingsDataStore.setAccessToken(data.accessToken) }
            .toDataState().map { loginResponseDto -> loginResponseDto.accessToken }
    }

    override suspend fun logout() {
        // TODO: logout API endpoint
        settingsDataStore.clearAccessToken()
    }
}