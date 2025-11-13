package nl.pcstet.navigation.onboarding.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import kotlinx.serialization.Serializable
import nl.pcstet.navigation.onboarding.presentation.screens.OnboardingApiInputScreen
import nl.pcstet.navigation.onboarding.presentation.screens.OnboardingApiTestScreen
import nl.pcstet.navigation.onboarding.presentation.screens.OnboardingWelcomeScreen
import org.koin.compose.viewmodel.koinViewModel

sealed interface OnboardingRoute {
    @Serializable
    data object Graph : OnboardingRoute

    @Serializable
    data object Welcome : OnboardingRoute

    @Serializable
    data object ApiInput : OnboardingRoute

    @Serializable
    data object ApiTest : OnboardingRoute
}

@Composable
fun OnboardingNavigation(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()
    val onboardingViewModel = koinViewModel<OnboardingViewModel>()
    val apiInputUiState by onboardingViewModel.apiInputUiState.collectAsState()
    val apiTestUiState by onboardingViewModel.apiTestUiState.collectAsState()

    NavHost(
        navController = navController,
        startDestination = OnboardingRoute.Graph,
        modifier = modifier
    ) {
        navigation<OnboardingRoute.Graph>(
            startDestination = OnboardingRoute.Welcome
        ) {
            composable<OnboardingRoute.Welcome> {
                OnboardingWelcomeScreen(
                    onNextClick = { navController.navigate(OnboardingRoute.ApiInput) }
                )
            }
            composable<OnboardingRoute.ApiInput> {
                OnboardingApiInputScreen(
                    apiInputUiState = apiInputUiState,
                    onApiSchemeChange = { onboardingViewModel.changeApiScheme(it) },
                    onApiHostChange = { onboardingViewModel.changeApiHost(it) },
                    onApiPathChange = {onboardingViewModel.changeApiPath(it)},
                    onNextClicked = { navController.navigate(OnboardingRoute.ApiTest) },
                )
            }
            composable<OnboardingRoute.ApiTest> {
                OnboardingApiTestScreen(
                    apiTestUiState = apiTestUiState,
                    startApiTest = onboardingViewModel::startApiTest,
                    onNavigateBack = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}
