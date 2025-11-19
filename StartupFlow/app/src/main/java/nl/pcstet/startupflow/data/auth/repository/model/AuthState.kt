package nl.pcstet.startupflow.data.auth.repository.model

sealed interface AuthState {
    data class Authenticated(val accessToken: String) : AuthState
    data object Unauthenticated : AuthState
    data object Loading : AuthState
}
