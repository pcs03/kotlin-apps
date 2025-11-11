package nl.pcstet.navigation.navigation.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import nl.pcstet.navigation.auth.data.repository.AuthRepository

class RootViewModel(
    private val authRepository: AuthRepository,
) : ViewModel() {
    enum class AuthState { LOADING, AUTHENTICATED, UNAUTHENTICATED }

    val authState: StateFlow<AuthState> = authRepository.isAuthenticated
        .onEach { Log.d("VM", it.toString()) }
        .map { isAuthenticated ->
            if (isAuthenticated) AuthState.AUTHENTICATED
            else AuthState.UNAUTHENTICATED
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = AuthState.LOADING
        )

    init {
        viewModelScope.launch {
            authState.collect { value ->
                Log.d("VM", "AS: $value")
            }
        }
    }
}