package nl.pcstet.startupflow.ui.auth.feature.onboardingtest

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable

@Serializable
data class OnboardingApiTestRoute(
    val apiUrl: String,
)

fun NavController.navigateToApiTestRoute(apiUrl: String, navOptions: NavOptions? = null) {
    navigate(OnboardingApiTestRoute(apiUrl = apiUrl), navOptions = navOptions)
}

data class OnboardingApiTestArgs(
    val apiUrl: String,
)

fun SavedStateHandle.toOnboardingApiTestArgs(): OnboardingApiTestArgs {
    val route = this.toRoute<OnboardingApiTestRoute>()
    return OnboardingApiTestArgs(apiUrl = route.apiUrl)
}

fun NavGraphBuilder.onboardingApiTestDestination(
    onOnboardingFinish: () -> Unit,
    onNavigateBack: () -> Unit,
) {
    composable<OnboardingApiTestRoute> {
        OnboardingApiTestScreen(
            onApiTestFinish = onOnboardingFinish,
            onNavigateBack = onNavigateBack,
        )
    }
}