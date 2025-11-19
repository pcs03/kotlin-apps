package nl.pcstet.startupflow.data.auth.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import nl.pcstet.startupflow.data.auth.repository.model.ApiTestResult
import nl.pcstet.startupflow.data.auth.repository.model.AuthState
import nl.pcstet.startupflow.data.auth.repository.model.LoginCredentials
import nl.pcstet.startupflow.data.utils.DataState

interface AuthRepository {
    val authState: StateFlow<AuthState>

    val apiUrl: Flow<String?>
    val rememberedEmail: Flow<String?>
    val showWelcomeScreen: Flow<Boolean>

    suspend fun setLandingScreenValues(
        apiUrl: String,
        email: String,
        rememberEmail: Boolean
    )

    suspend fun setRememberedEmail(email: String)

    suspend fun checkAccessTokenValid(): DataState<Any>

    suspend fun testApiUrlValid(apiUrl: String): Flow<ApiTestResult>
    suspend fun login(credentials: LoginCredentials): DataState<Any>
    suspend fun logout()
}