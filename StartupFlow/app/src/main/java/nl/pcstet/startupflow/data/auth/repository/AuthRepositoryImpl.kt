package nl.pcstet.startupflow.data.auth.repository

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import nl.pcstet.startupflow.data.auth.datasource.network.AuthApiService
import nl.pcstet.startupflow.data.auth.repository.model.ApiTestResult
import nl.pcstet.startupflow.data.auth.repository.model.AuthState
import nl.pcstet.startupflow.data.auth.repository.model.LoginCredentials
import nl.pcstet.startupflow.data.auth.repository.model.toLoginRequestDto
import nl.pcstet.startupflow.data.core.datasource.disk.SettingsDataSource
import nl.pcstet.startupflow.data.core.datasource.network.utils.ApiException
import nl.pcstet.startupflow.data.core.datasource.network.utils.ApiResult
import nl.pcstet.startupflow.data.core.datasource.network.utils.onFailure
import nl.pcstet.startupflow.data.core.datasource.network.utils.onSuccess
import nl.pcstet.startupflow.data.core.datasource.network.utils.toDataState
import nl.pcstet.startupflow.data.core.manager.dispatcher.DispatcherManager
import nl.pcstet.startupflow.data.utils.DataState
import nl.pcstet.startupflow.data.utils.map

class AuthRepositoryImpl(
    private val settingsDataSource: SettingsDataSource,
    private val authApiService: AuthApiService,
    dispatcherManager: DispatcherManager,
) : AuthRepository {
    private val unconfinedScope = CoroutineScope(dispatcherManager.unconfined)
    private val ioScope = CoroutineScope(dispatcherManager.io)

    // TODO: There is currently no handling for an expired access token
    override val authState: StateFlow<AuthState> = combine(
        settingsDataSource.apiUrl,
        settingsDataSource.accessToken,
    ) { apiUrl, accessToken ->
        if (apiUrl.isNullOrEmpty() || accessToken.isNullOrEmpty()) {
            AuthState.Unauthenticated
        } else {
            AuthState.Authenticated(accessToken = accessToken)
        }
    }.stateIn(
        scope = unconfinedScope,
        started = SharingStarted.Eagerly,
        initialValue = AuthState.Loading
    )

    override val apiUrl: Flow<String?> = settingsDataSource.apiUrl
    override val rememberedEmail: Flow<String?> = settingsDataSource.emailAddress
    override val showWelcomeScreen: Flow<Boolean> =
        settingsDataSource.hasUserLoggedInOrCreatedAccount.map { !it }

    override suspend fun setRememberedEmail(email: String) {
        settingsDataSource.setEmailAddress(email)
    }

    override suspend fun setLandingScreenValues(
        apiUrl: String,
        email: String,
        rememberEmail: Boolean
    ) {
        if (rememberEmail) {
            settingsDataSource.setEmailAddress(email)
        }
        settingsDataSource.setApiUrl(apiUrl)
    }

    override suspend fun checkAccessTokenValid(): DataState<Any> {
        val apiUrl =
            settingsDataSource.apiUrl.first() ?: throw IllegalStateException("API URL not found.")
        val accessToken = settingsDataSource.accessToken.first()
            ?: throw IllegalStateException("Must be logged in.")
        val result = authApiService.authenticate(apiUrl, accessToken)

        return result
            .onFailure { settingsDataSource.clearAccessToken() }
            .toDataState()
    }

    override suspend fun login(credentials: LoginCredentials): DataState<String> {
        val apiUrl = settingsDataSource.apiUrl.first()
        if (apiUrl.isNullOrEmpty()) {
            return DataState.Error(ApiException.Generic("Illegal state. Login attempted, but no API URL found."))
        }

        val result = authApiService.login(apiUrl, credentials.toLoginRequestDto())
        return result
            .onSuccess { data -> settingsDataSource.setAccessToken(data.accessToken) }
            .toDataState().map { loginResponseDto -> loginResponseDto.accessToken }
    }

    override suspend fun testApiUrlValid(apiUrl: String): Flow<ApiTestResult> = flow {
        emit(ApiTestResult.Loading)
        val apiResult = authApiService.test(apiUrl)

        Log.d("AuthRepo", apiResult.toString())

        val apiTestResult = when(apiResult) {
            is ApiResult.Success -> ApiTestResult.Success
            is ApiResult.Failure -> ApiTestResult.Failure(message = apiResult.error.message)
        }
        emit(apiTestResult)
    }

    override suspend fun logout() {
        // TODO: logout API endpoint
        settingsDataSource.clearAccessToken()
    }
}