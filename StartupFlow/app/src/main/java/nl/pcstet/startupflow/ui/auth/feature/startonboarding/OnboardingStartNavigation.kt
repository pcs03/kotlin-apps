package nl.pcstet.startupflow.ui.auth.feature.startonboarding

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object OnboardingStartRoute

fun NavController.navigateToOnboardingStart(navOptions: NavOptions? = null) {
    navigate(OnboardingStartRoute, navOptions = navOptions)
}

fun NavGraphBuilder.onboardingStartDestination(
    onNavigateToApiInput: () -> Unit,
) {
    composable<OnboardingStartRoute> {
        OnboardingStartScreen(
            onNavigateToApiInput = onNavigateToApiInput,
        )
    }
}