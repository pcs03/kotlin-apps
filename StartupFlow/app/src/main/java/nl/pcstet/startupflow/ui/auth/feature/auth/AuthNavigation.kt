package nl.pcstet.startupflow.ui.auth.feature.auth

import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import kotlinx.serialization.Serializable
import nl.pcstet.startupflow.core.presentation.navigation.AppGraph
import nl.pcstet.startupflow.core.presentation.utils.sharedViewModel
import nl.pcstet.startupflow.ui.auth.feature.onboardinginput.OnboardingApiInputScreen
import nl.pcstet.startupflow.ui.auth.feature.onboardinginput.OnboardingViewModel
import nl.pcstet.startupflow.ui.auth.feature.onboardingtest.OnboardingApiTestScreen
import nl.pcstet.startupflow.ui.auth.feature.startonboarding.OnboardingStartScreen

@Serializable
data object AuthGraphRoute

fun NavGraphBuilder.authGraph(
    navController: NavHostController,
) {
    navigation<AuthGraphRoute>(
        startDestination =
    )
}

fun NavController.navigateToOnboardingGraph(navOptions: NavOptionsBuilder.() -> Unit = {}) =
    navigate(route = AppGraph.Onboarding, navOptions)

fun NavController.navigateToOnboardingWelcome(navOptions: NavOptionsBuilder.() -> Unit = {}) =
    navigate(route = OnboardingRoute.Welcome)

fun NavController.navigateToOnboardingInput(navOptions: NavOptionsBuilder.() -> Unit = {}) =
    navigate(route = OnboardingRoute.ApiInput)

fun NavController.navigateToOnboardingTest(navOptions: NavOptionsBuilder.() -> Unit = {}) =
    navigate(route = OnboardingRoute.ApiTest)

fun NavGraphBuilder.onboardingDestination(
    navController: NavController,
    onNavigateToOnboardingInput: () -> Unit,
    onNavigateToOnboardingTest: () -> Unit,
    onNavigateBack: () -> Unit,
    onOnboardingComplete: () -> Unit,
) {
    Log.d("OnboardingNavigation", "Composition")
    navigation<AppGraph.Onboarding>(
        startDestination = OnboardingRoute.Welcome
    ) {
        composable<OnboardingRoute.Welcome> {
            OnboardingStartScreen(
                onNavigateToApiInput = onNavigateToOnboardingInput
            )
        }
        composable<OnboardingRoute.ApiInput> { backStackEntry ->
            val onboardingViewModel =
                backStackEntry.sharedViewModel<OnboardingViewModel>(navController)
            val apiInputUiState by onboardingViewModel.uiState.collectAsState()

            OnboardingApiInputScreen(
                apiInputUiState = apiInputUiState,
                onApiProtocolChange = { onboardingViewModel.changeApiProtocol(it) },
                onApiHostChange = { onboardingViewModel.changeApiHost(it) },
                onApiPathChange = { onboardingViewModel.changeApiPath(it) },
                onNextClicked = onNavigateToOnboardingTest,
            )
        }
        composable<OnboardingRoute.ApiTest> { backStackEntry ->
            val onboardingViewModel =
                backStackEntry.sharedViewModel<OnboardingViewModel>(navController)
            val apiTestUiState by onboardingViewModel.apiTestUiState.collectAsState()

            OnboardingApiTestScreen(
                apiTestUiState = apiTestUiState,
                startApiTest = onboardingViewModel::testApiUrl,
                onApiTestFinish = onOnboardingComplete,
                onNavigateBack = onNavigateBack,
            )
        }
    }
}
