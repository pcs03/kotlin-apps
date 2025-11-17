package nl.pcstet.startupflow.ui.core.feature.appnav

import kotlinx.coroutines.flow.update
import nl.pcstet.startupflow.data.auth.repository.AuthRepository
import nl.pcstet.startupflow.data.auth.repository.model.AuthState
import nl.pcstet.startupflow.ui.core.base.BaseViewModel

class AppNavViewModel(
    private val authRepository: AuthRepository,
): BaseViewModel<AppNavState, Unit, AppNavAction>(
    initialState = AppNavState.Splash
) {
    override fun handleAction(action: AppNavAction) {
        when(action) {
            is AppNavAction.AppStateUpdateReceive -> handleAppStateUpdateReceive(action)
        }
    }

    private fun handleAppStateUpdateReceive(
        action: AppNavAction.AppStateUpdateReceive
    ) {
        val authState = action.authState
        val updatedAppNavState = when(authState) {
            is AuthState.Loading -> AppNavState.Splash
            is AuthState.Unauthenticated -> AppNavState.Auth
            is AuthState.Locked -> AppNavState.AppLocked
            is AuthState.Unlocked -> AppNavState.AppUnlocked
        }

        mutableStateFlow.update { updatedAppNavState }
    }
}



//class AppNavViewModel(
//    private val authRepository: AuthRepository
//) : ViewModel() {
//    private val _authState = MutableStateFlow<AuthState>(AuthState.Loading)
//    val authState = _authState.asStateFlow()
//
//    init {
//        checkAuth()
//    }
//
//    fun checkAuth() {
//        viewModelScope.launch {
//            _authState.value = authRepository.getAuthState()
//        }
//    }
//
//    fun onOnboardingComplete() {
//        checkAuth()
//    }
//
//    fun onLoginSuccess() {
//        _authState.value = AuthState.Authenticated
//    }
//
//    fun onSessionExpired() {
//        viewModelScope.launch {
//            authRepository.clearAccessToken()
//            _authState.value = AuthState.Unauthenticated
//        }
//    }
//}

sealed interface AppNavState {

    // Show Auth Graph
    data object Auth : AppNavState

    // Show Auth Graph including Welcome Screen
    data object AuthWithWelcome : AppNavState

    // Show Unlock Screen
    data object AppLocked : AppNavState

    // Go to home screen
    data object AppUnlocked : AppNavState

    // Show Splash screen
    data object Splash : AppNavState
}

sealed interface AppNavAction {
    data class AppStateUpdateReceive(
        val authState: AuthState
    ) : AppNavAction
}