package nl.pcstet.navigation.core.data.utils

sealed interface AuthState {
    data object Authenticated : AuthState
    data class Unauthenticated(val message: String) : AuthState
    data object Loading : AuthState
    data object InvalidToken: AuthState
    data object Unknown : AuthState
}