package nl.pcstet.navigation.main.presentation

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import kotlinx.serialization.Serializable
import nl.pcstet.navigation.core.presentation.components.LoadingIndicator
import nl.pcstet.navigation.onboarding.presentation.OnboardingRoute
import nl.pcstet.navigation.onboarding.presentation.onboardingDestination
import org.koin.compose.viewmodel.koinViewModel

sealed interface MainRoute {
    @Serializable
    data object Graph : MainRoute
    @Serializable
    data object Loading : MainRoute
}

@Composable
fun MainNavigation(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = MainRoute.Graph
    ) {
        navigation<MainRoute.Graph>(startDestination = MainRoute.Loading) {
            composable<MainRoute.Loading> { backStackEntry ->
                val mainViewModel = koinViewModel<MainViewModel>()
                val onboardingState by mainViewModel.onboardingState.collectAsState()

                when (onboardingState) {
                    is OnboardingState.Loading -> {
                        LoadingIndicator(Modifier.fillMaxSize())
                    }

                    is OnboardingState.OnboardingRequired -> {
                        LaunchedEffect(onboardingState) {
                            Log.d("NestedNavigation", "Onboarding required, navigating to onboarding")
                            navController.navigate(OnboardingRoute.Graph) {
                                popUpTo(MainRoute.Graph)
                            }
                        }
                    }

                    is OnboardingState.OnboardingComplete -> {
                        LaunchedEffect(onboardingState) {
                            val onboardingState = onboardingState as OnboardingState.OnboardingComplete
                            mainViewModel.initializeUnauthenticatedApiClientHolder(onboardingState.backendUrl)
                            navController.navigate(AppRootRoute.Graph) {
                                popUpTo(MainRoute.Graph)
                            }
                        }
                    }
                }
            }
            onboardingDestination(
                navController = navController,
                onOnboardingComplete = {
                    navController.navigate(MainRoute.Graph) {
                        popUpTo(MainRoute.Graph)
                    }
                }
            )
            appRootDestination(
                navController = navController,
                onOnboardingRequired = {
                    navController.navigate(MainRoute.Graph) {
                        popUpTo(MainRoute.Graph)
                    }
                }
            )
        }
    }
}