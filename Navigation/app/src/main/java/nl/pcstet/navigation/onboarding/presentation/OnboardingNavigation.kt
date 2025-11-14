package nl.pcstet.navigation.onboarding.presentation

import android.util.Log
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import kotlinx.serialization.Serializable
import nl.pcstet.navigation.main.presentation.AppRootRoute
import nl.pcstet.navigation.main.presentation.MainViewModel
import nl.pcstet.navigation.main.presentation.utils.sharedViewModel
import nl.pcstet.navigation.onboarding.presentation.screens.OnboardingApiInputScreen
import nl.pcstet.navigation.onboarding.presentation.screens.OnboardingApiTestScreen
import nl.pcstet.navigation.onboarding.presentation.screens.OnboardingWelcomeScreen

sealed interface OnboardingRoute {
    @Serializable
    data object Graph : OnboardingRoute

    @Serializable
    data object Welcome : OnboardingRoute

    @Serializable
    data object ApiInput : OnboardingRoute

    @Serializable
    data object ApiTest : OnboardingRoute

    @Serializable
    data object Complete : OnboardingRoute
}

fun NavGraphBuilder.onboardingDestination(
    navController: NavController,
    onOnboardingComplete: () -> Unit,
) {
    navigation<OnboardingRoute.Graph>(startDestination = OnboardingRoute.Welcome) {
        composable<OnboardingRoute.Welcome> { backStackEntry ->
            Log.d("OnboardingNavigation", "Welcome")
            OnboardingWelcomeScreen(
                onNextClick = { navController.navigate(OnboardingRoute.ApiInput) }
            )
        }

        composable<OnboardingRoute.ApiInput> { backStackEntry ->
            val onboardingViewModel =
                backStackEntry.sharedViewModel<OnboardingViewModel>(navController)
            val apiInputUiState by onboardingViewModel.apiInputUiState.collectAsState()

            OnboardingApiInputScreen(
                apiInputUiState = apiInputUiState,
                onApiSchemeChange = { onboardingViewModel.changeApiScheme(it) },
                onApiHostChange = { onboardingViewModel.changeApiHost(it) },
                onApiPathChange = { onboardingViewModel.changeApiPath(it) },
                onNextClicked = { navController.navigate(OnboardingRoute.ApiTest) },
            )
        }
        composable<OnboardingRoute.ApiTest> { backStackEntry ->
            val onboardingViewModel =
                backStackEntry.sharedViewModel<OnboardingViewModel>(navController)
            val apiTestUiState by onboardingViewModel.apiTestUiState.collectAsState()

            OnboardingApiTestScreen(
                apiTestUiState = apiTestUiState,
                startApiTest = onboardingViewModel::startApiTest,
                onApiTestFinish = {
                    navController.navigate(AppRootRoute.Graph) {
                        popUpTo(OnboardingRoute.Graph) {
                            inclusive = true
                        }
                    }
                },
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
        composable<OnboardingRoute.Complete> {
            Button(
                onClick = onOnboardingComplete
            ) {
                Text(text = "Finish")
            }
        }

    }
}
