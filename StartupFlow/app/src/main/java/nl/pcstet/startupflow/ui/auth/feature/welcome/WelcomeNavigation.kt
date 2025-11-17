package nl.pcstet.startupflow.ui.auth.feature.welcome

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object WelcomeRoute

fun NavController.navigateToWelcome(navOptions: NavOptions? = null) {
    navigate(WelcomeRoute, navOptions = navOptions)
}

fun NavGraphBuilder.welcomeDestination(
    onNavigateToLogin: () -> Unit,
) {
    composable<WelcomeRoute> {
        WelcomeScreen(
            onNavigateToLogin = onNavigateToLogin,
        )
    }
}