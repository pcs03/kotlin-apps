package nl.pcstet.startupflow.ui.auth.feature.landing

import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import nl.pcstet.startupflow.ui.core.base.utils.EventsEffect
import org.koin.compose.viewmodel.koinViewModel

@Serializable
data object LandingRoute

fun NavController.navigateToLanding(navOptions: NavOptions? = null) {
    navigate(LandingRoute, navOptions = navOptions)
}

fun NavGraphBuilder.landingDestination(
    onNavigateToLogin: (email: String) -> Unit,
) {
    composable<LandingRoute> {
        val viewModel: LandingViewModel = koinViewModel()
        val state by viewModel.stateFlow.collectAsStateWithLifecycle()

        EventsEffect(viewModel = viewModel) { event ->
            when (event) {
                is LandingEvent.NavigateToLogin -> onNavigateToLogin(event.email)
            }
        }

        LandingScreen(
            state = state,
            onEmailInputChanged = remember(viewModel) {
                { viewModel.trySendAction(LandingAction.EmailInputChanged(it)) }
            },
            onApiUrlInputChanged = remember(viewModel) {
                { viewModel.trySendAction(LandingAction.ApiUrlInputChanged(it)) }
            },
            onRememberEmailToggle = remember(viewModel) {
                { viewModel.trySendAction(LandingAction.RememberEmailSwitchClicked(it)) }
            },
            onContinueClick = remember(viewModel) {
                { viewModel.trySendAction(LandingAction.ContinueButtonClick) }
            },
        )
    }
}