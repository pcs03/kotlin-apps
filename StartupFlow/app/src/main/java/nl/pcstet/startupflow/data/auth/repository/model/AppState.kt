package nl.pcstet.startupflow.data.auth.repository.model

sealed interface AppState {
    data object Loading : AppState
    data class Auth(
        val email: String?,
        val authState: AuthState
    ) : AppState
    data class OnboardingRequired(
        val showWelcomeScreen: Boolean
    ) : AppState
}