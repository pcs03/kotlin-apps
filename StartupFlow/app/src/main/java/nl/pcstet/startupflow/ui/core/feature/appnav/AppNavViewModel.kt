package nl.pcstet.startupflow.ui.core.feature.appnav

import android.util.Log
import androidx.lifecycle.viewModelScope
import io.ktor.util.valuesOf
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import nl.pcstet.startupflow.data.auth.repository.AuthRepository
import nl.pcstet.startupflow.data.auth.repository.model.AuthState
import nl.pcstet.startupflow.ui.core.base.BaseViewModel

class AppNavViewModel(
    private val authRepository: AuthRepository,
) : BaseViewModel<AppNavState, Unit, AppNavAction>(
    initialState = AppNavState.Splash
) {
    init {
        combine(
            authRepository.authState,
            authRepository.showWelcomeScreen,
        ) { authState, showWelcomeScreen ->
            AppNavAction.Internal.AppStateUpdateReceive(authState, showWelcomeScreen)
        }
            .distinctUntilChanged()
            .onEach { value ->
                Log.d("AppNavViewModel", "AppNav Flow: $value")
            }
            .onEach(::handleAction)
            .launchIn(viewModelScope)
    }

    override fun handleAction(action: AppNavAction) {
        when (action) {
            is AppNavAction.Internal.AppStateUpdateReceive -> handleAppStateUpdateReceive(action)
        }
    }

    private fun handleAppStateUpdateReceive(
        action: AppNavAction.Internal.AppStateUpdateReceive,
    ) {
        val authState = action.authState
        val updatedAppNavState = when (authState) {
            is AuthState.Loading -> AppNavState.Splash
            is AuthState.Authenticated -> AppNavState.LoggedIn
            is AuthState.Unauthenticated -> {
                when (action.showWelcomeScreen) {
                    true -> AppNavState.AuthWithWelcome
                    false -> AppNavState.Auth
                }
            }
        }

        mutableStateFlow.update { updatedAppNavState }
    }
}


sealed interface AppNavState {

    // Show Auth Graph
    data object Auth : AppNavState

    // Show Auth Graph including Welcome Screen
    data object AuthWithWelcome : AppNavState

    // Go to home screen
    data object LoggedIn : AppNavState

    // Show Splash screen
    data object Splash : AppNavState
}

sealed interface AppNavAction {
    sealed interface Internal {
        data class AppStateUpdateReceive(
            val authState: AuthState,
            val showWelcomeScreen: Boolean,
        ) : AppNavAction

    }
}