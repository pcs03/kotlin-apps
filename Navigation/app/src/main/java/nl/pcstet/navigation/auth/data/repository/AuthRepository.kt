package nl.pcstet.navigation.auth.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import nl.pcstet.navigation.core.data.utils.AuthState

interface AuthRepository {
    val authState: StateFlow<AuthState>
    suspend fun login(email: String, password: String)
    suspend fun logout()
}