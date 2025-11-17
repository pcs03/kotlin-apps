package nl.pcstet.startupflow.ui.core.feature.appnav

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import nl.pcstet.startupflow.data.auth.repository.AuthRepository
import nl.pcstet.startupflow.data.auth.repository.model.AuthState

class AppNavViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _authState = MutableStateFlow<AuthState>(AuthState.Loading)
    val authState = _authState.asStateFlow()

    init {
        checkAuth()
    }

    fun checkAuth() {
        viewModelScope.launch {
            _authState.value = authRepository.getAuthState()
        }
    }

    fun onOnboardingComplete() {
        checkAuth()
    }

    fun onLoginSuccess() {
        _authState.value = AuthState.Authenticated
    }

    fun onSessionExpired() {
        viewModelScope.launch {
            authRepository.clearAccessToken()
            _authState.value = AuthState.Unauthenticated
        }
    }
}