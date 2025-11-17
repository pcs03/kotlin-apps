package nl.pcstet.startupflow.ui.auth.feature.welcome

import nl.pcstet.startupflow.ui.core.base.BaseViewModel

class WelcomeViewModel() : BaseViewModel<Unit, WelcomeEvent, WelcomeAction>(
    initialState = Unit,
) {
    override fun handleAction(action: WelcomeAction) {
        when (action) {
            is WelcomeAction.LoginClick -> handleLoginClick()
        }
    }

    private fun handleLoginClick() {
        sendEvent(WelcomeEvent.NavigateToLogin)
    }
}


sealed interface WelcomeEvent {
    data object NavigateToLogin : WelcomeEvent
}

sealed interface WelcomeAction {
    data object LoginClick : WelcomeAction
}
