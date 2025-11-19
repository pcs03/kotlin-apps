package nl.pcstet.startupflow.ui.auth.feature.welcome

import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import nl.pcstet.startupflow.ui.core.base.utils.EventsEffect
import org.koin.compose.viewmodel.koinViewModel

@Serializable
data object WelcomeRoute

fun NavController.navigateToWelcome(navOptions: NavOptions? = null) {
    navigate(WelcomeRoute, navOptions = navOptions)
}

fun NavGraphBuilder.welcomeDestination(
    onNavigateToLogin: () -> Unit,
) {
    composable<WelcomeRoute> {
        val viewModel: WelcomeViewModel = koinViewModel()

        EventsEffect(viewModel = viewModel) { event ->
            when(event) {
                is WelcomeEvent.NavigateToLogin -> onNavigateToLogin()
            }
        }

        WelcomeScreen(
            onLoginClick = remember(viewModel) {
                { viewModel.trySendAction(WelcomeAction.LoginClick) }
            },
        )
    }
}