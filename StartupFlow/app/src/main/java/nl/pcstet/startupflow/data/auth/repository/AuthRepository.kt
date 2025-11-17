package nl.pcstet.startupflow.data.auth.repository

import kotlinx.coroutines.flow.StateFlow
import nl.pcstet.startupflow.data.auth.repository.model.AuthState
import nl.pcstet.startupflow.data.auth.repository.model.LoginCredentials
import nl.pcstet.startupflow.data.utils.DataState

interface AuthRepository {
    val authStateFlow: StateFlow<AuthState>
    suspend fun getAuthState(): AuthState
    suspend fun login(credentials: LoginCredentials): DataState<String>
    suspend fun clearAccessToken()
}