package nl.pcstet.startupflow.data.auth.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import nl.pcstet.startupflow.data.auth.repository.model.ApiTestResult
import nl.pcstet.startupflow.data.auth.repository.model.AuthState
import nl.pcstet.startupflow.data.auth.repository.model.LoginCredentials
import nl.pcstet.startupflow.data.utils.DataState

interface AuthRepository {
    // TODO: This should return Flow of Authenticated or Unauthenticated
    val authState: StateFlow<AuthState>

    val apiUrl: Flow<String?>
    val rememberedEmail: Flow<String?>
    val showWelcomeScreen: Flow<Boolean>

    // TODO: Flow of result
    suspend fun checkAccessTokenValid(): DataState<Any>
    // TODO: FLow of Result
    suspend fun testApiUrlValid(apiUrl: String): Flow<ApiTestResult>
    // TODO: Flow of result
    suspend fun login(apiUrl: String, credentials: LoginCredentials, rememberMe: Boolean): DataState<String>
    // TODO: Flow of result
    suspend fun logout()
}