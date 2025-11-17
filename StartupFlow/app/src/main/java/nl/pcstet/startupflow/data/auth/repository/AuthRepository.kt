package nl.pcstet.startupflow.data.auth.repository

import kotlinx.coroutines.flow.StateFlow
import nl.pcstet.startupflow.data.auth.repository.model.AuthState
import nl.pcstet.startupflow.data.auth.repository.model.LoginCredentials
import nl.pcstet.startupflow.data.utils.DataState

interface AuthRepository {
    val authState: StateFlow<AuthState>

    suspend fun checkAccessTokenValid(): DataState<Any>
    suspend fun login(credentials: LoginCredentials): DataState<Any>
    suspend fun logout()
}