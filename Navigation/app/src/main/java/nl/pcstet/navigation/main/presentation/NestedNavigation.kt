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
import nl.pcstet.navigation.core.presentation.components.LoadingIndicator
import nl.pcstet.navigation.onboarding.presentation.OnboardingRoute
import nl.pcstet.navigation.onboarding.presentation.onboardingDestination
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun NestedNavigation(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = Route.MainLoading
    ) {
        composable<Route.MainLoading> { ackStackEntry ->
            val mainViewModel = koinViewModel<NestedMainViewModel>()
            val onboardingState by mainViewModel.onboardingState.collectAsState()

            when (onboardingState) {
                is OnboardingState.Loading -> {
                    LoadingIndicator(Modifier.fillMaxSize())
                }

                is OnboardingState.OnboardingRequired -> {
                    LaunchedEffect(onboardingState) {
                        Log.d("NestedNavigation", "Onboarding required, navigating to onboarding")
                        navController.navigate(OnboardingRoute.Graph) {
                            popUpTo(Route.MainLoading) {
                                inclusive = true
                            }
                        }
                    }
                }

                is OnboardingState.OnboardingComplete -> {
                    LaunchedEffect(onboardingState) {

                        // TODO: Ensure API client holder is instantiated

                        navController.navigate(AppRootRoute.Graph) {
                            popUpTo(Route.MainLoading) {
                                inclusive = true
                            }
                        }
                    }
                }
            }
        }
        onboardingDestination(
            navController = navController,
            onOnboardingComplete = {
                navController.navigate(Route.AuthRootGraph) {
                    popUpTo(OnboardingRoute.Graph) {
                        inclusive = true
                    }
                }
            }
        )
        appRootDestination(
            navController = navController
        )
    }
}

//    when () {
//        is AuthState.Loading, is AuthState.Unknown -> {
//            LoadingIndicator(Modifier.fillMaxSize())
//        }
//
//        is AuthState.Unauthenticated, is AuthState.Authenticated, is AuthState.InvalidToken -> {
//            val startDestination = if (authState is AuthState.Authenticated) {
//                Route.AppRootGraph
//            } else {
//                Route.AuthGraph
//            }
//
//            NavHost(
//                navController = navController,
//                startDestination = startDestination,
//            ) {
//                navigation<Route.AuthGraph>(
//                    startDestination = Route.LoginScreen
//                ) {
//                    composable<Route.LoginScreen> {
//                        val loginViewModel = koinViewModel<LoginViewModel>()
//                        LoginScreen(viewModel = loginViewModel)
//                    }
//                }
//
//                navigation<Route.AppRootGraph>(
//                    startDestination = Route.HomeScreen
//                ) {
//                    composable<Route.HomeScreen> {
//                        val homeViewModel = koinViewModel<HomeViewModel>()
//                        HomeScreen(viewModel = homeViewModel)
//                    }
//                }
//            }
//        }
//    }
//
//}

//@Composable
//inline fun <reified T : ViewModel> NavBackStackEntry.RootViewModel