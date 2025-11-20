package nl.pcstet.startupflow.ui.main.feature.home

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import nl.pcstet.startupflow.data.auth.repository.AuthRepository
import nl.pcstet.startupflow.ui.core.base.BaseViewModel

class HomeViewModel(
    private val authRepository: AuthRepository,
) : BaseViewModel<HomeState, Unit, HomeAction>(HomeState()) {
    override fun handleAction(action: HomeAction) {
        when(action) {
            is HomeAction.LogoutButtonPressed -> handleLogout()
        }
    }

    private fun handleLogout() {
        viewModelScope.launch {
            authRepository.logout()
        }
    }
}

data class HomeState(
    val message: String = "Welcome to home"
)

sealed interface HomeAction {
    data object LogoutButtonPressed : HomeAction
}