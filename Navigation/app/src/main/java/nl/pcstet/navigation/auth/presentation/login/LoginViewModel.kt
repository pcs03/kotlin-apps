package nl.pcstet.navigation.auth.presentation.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import nl.pcstet.navigation.auth.data.repository.AuthRepository
import nl.pcstet.navigation.core.data.utils.AuthState

sealed interface LoginUiState {
    data object Idle : LoginUiState
    data object Loading : LoginUiState
    data class Error(val message: String) : LoginUiState
}

class LoginViewModel(
    private val authRepository: AuthRepository,
) : ViewModel() {
    val authState = authRepository.authState
    val uiState: StateFlow<LoginUiState> = authState.map { authState ->
        Log.d("LoginViewModel", "Received authState: $authState")
        when (authState) {
            is AuthState.InvalidToken, is AuthState.Authenticated -> LoginUiState.Idle
            is AuthState.Loading, is AuthState.Unknown -> LoginUiState.Loading
            is AuthState.Unauthenticated -> LoginUiState.Error(authState.message)
        }
    }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = LoginUiState.Idle
        )

    val email = MutableStateFlow("")
    val password = MutableStateFlow("")

    fun login() {
        if (uiState.value == LoginUiState.Loading) return

        viewModelScope.launch {
            authRepository.login(email = email.value, password = password.value)
        }
    }
}