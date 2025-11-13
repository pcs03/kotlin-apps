package nl.pcstet.navigation.main.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import nl.pcstet.navigation.auth.data.repository.AuthRepository
import nl.pcstet.navigation.core.data.network.ApiClientHolder
import nl.pcstet.navigation.core.data.utils.AuthState

class RootViewModel(
    private val authRepository: AuthRepository,
) : ViewModel() {
    val authState: StateFlow<AuthState> = authRepository.authState
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = AuthState.Loading
        )

    init {
        viewModelScope.launch {
            authState.collect { value ->
                Log.d("RootViewModel", "authStateFlow: $value")
            }
        }
    }
}