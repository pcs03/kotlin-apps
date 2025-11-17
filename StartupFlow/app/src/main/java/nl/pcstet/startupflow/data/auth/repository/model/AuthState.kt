package nl.pcstet.startupflow.data.auth.repository.model

sealed interface AuthState {
    data class Locked(val apiUrl: String, val emailAddress: String?) : AuthState
    data class Unlocked(val apiUrl: String, val accessToken: String) : AuthState
    data object Unauthenticated : AuthState
    data object Loading : AuthState
}
