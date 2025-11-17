package nl.pcstet.startupflow.data.auth.repository.model

sealed interface AuthState {
    data object Authenticated : AuthState
    data object Unauthenticated : AuthState
    data object OnboardingRequired : AuthState
    data object Loading : AuthState
}
