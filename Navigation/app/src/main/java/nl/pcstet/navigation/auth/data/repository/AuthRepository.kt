package nl.pcstet.navigation.auth.data.repository

import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    val isAuthenticated: Flow<Boolean>
    suspend fun login(email: String, password: String): Result<Unit>
    suspend fun logout(): Unit
}