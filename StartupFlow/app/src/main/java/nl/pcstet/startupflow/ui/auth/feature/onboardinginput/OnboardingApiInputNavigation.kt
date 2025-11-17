package nl.pcstet.startupflow.ui.auth.feature.onboardinginput

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object OnboardingApiInputRoute

fun NavController.navigateToApiInput(navOptions: NavOptions? = null) {
    navigate(OnboardingApiInputRoute, navOptions = navOptions)
}

fun NavGraphBuilder.onboardingApiInputDestination(
    onNavigateToApiTest: () -> Unit,
) {
    composable<OnboardingApiInputRoute> {
        OnboardingApiInputScreen(

        )
    }
}