package nl.pcstet.navigation.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import nl.pcstet.navigation.auth.data.repository.AuthRepository
import nl.pcstet.navigation.core.data.utils.DataState
import nl.pcstet.navigation.home.domain.UserRepository
import nl.pcstet.navigation.home.domain.model.User

class HomeViewModel(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository,
) : ViewModel() {

    val homeUiState: StateFlow<HomeUiState> = userRepository.getUser().map { dataState ->
        when (dataState) {
            is DataState.Loading -> HomeUiState.Loading
            is DataState.Error -> HomeUiState.Error(dataState.cause.errorMsg)
            is DataState.Success -> HomeUiState.HasUser(user = dataState.data)
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = HomeUiState.Loading
    )

    fun logout() {
        viewModelScope.launch {
            authRepository.logout()
        }
    }
}

sealed interface HomeUiState {
    data object Loading : HomeUiState
    data class HasUser(val user: User) : HomeUiState
    data class Error(val message: String) : HomeUiState
}