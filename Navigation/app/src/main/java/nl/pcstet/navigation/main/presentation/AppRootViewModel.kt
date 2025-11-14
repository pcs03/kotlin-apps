package nl.pcstet.navigation.main.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import nl.pcstet.navigation.auth.data.repository.AuthRepository
import nl.pcstet.navigation.core.data.utils.AuthState
import nl.pcstet.navigation.main.data.local.UserPreferencesDataStore

class RootViewModel(
    private val authRepository: AuthRepository,
    private val userPreferencesDataStore: UserPreferencesDataStore,
) : ViewModel() {
    private val authState: StateFlow<AuthState> = authRepository.authState
    val appRootState: StateFlow<AppRootState> =
        authState.combine(userPreferencesDataStore.backendUrl) { authState, backendUrl ->
            if (backendUrl == null) {
                AppRootState.OnboardingRequired
            } else {
                when (authState) {
                    is AuthState.Loading, is AuthState.Unknown -> AppRootState.Loading
                    is AuthState.InvalidToken, is AuthState.Unauthenticated -> AppRootState.Unauthenticated
                    is AuthState.Authenticated -> AppRootState.Authenticated(
                        backendUrl = backendUrl,
                        accessToken = authState.accessToken
                    )
                }
            }
        }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000L),
                initialValue = AppRootState.Loading
            )

    init {
        viewModelScope.launch {
            authState.collect { value ->
                Log.d("RootViewModel", "authStateFlow: $value")
            }
        }
    }
}

sealed interface AppRootState {
    data object Loading : AppRootState
    data object OnboardingRequired : AppRootState
    data class Authenticated(val backendUrl: String, val accessToken: String) : AppRootState
    data object Unauthenticated : AppRootState
}