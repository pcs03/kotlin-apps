package nl.pcstet.navigation.main.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import kotlinx.serialization.Serializable
import nl.pcstet.navigation.auth.presentation.authDestination
import nl.pcstet.navigation.auth.presentation.login.LoginScreen
import nl.pcstet.navigation.auth.presentation.login.LoginViewModel
import nl.pcstet.navigation.core.data.utils.AuthState
import nl.pcstet.navigation.core.presentation.components.LoadingIndicator
import nl.pcstet.navigation.home.presentation.HomeScreen
import nl.pcstet.navigation.home.presentation.HomeViewModel
import nl.pcstet.navigation.main.presentation.utils.sharedViewModel
import nl.pcstet.navigation.onboarding.presentation.OnboardingViewModel
import org.koin.compose.viewmodel.koinViewModel

sealed interface AppRootRoute {
    @Serializable
    data object Graph : AppRootRoute

    @Serializable
    data object Loading : AppRootRoute

    @Serializable
    data object Login : AppRootRoute

    @Serializable
    data object Home : AppRootRoute
}

fun NavGraphBuilder.appRootDestination(
    navController: NavHostController,
) {
    navigation<AppRootRoute.Graph>(startDestination = AppRootRoute.Loading) {
        composable<AppRootRoute.Loading> { backStackEntry ->
            val appRootViewModel = backStackEntry.sharedViewModel<RootViewModel>(navController)
            val authState by appRootViewModel.authState.collectAsState()

            when (authState) {
                is AuthState.Unknown, is AuthState.Loading -> {
                    LoadingIndicator(Modifier.fillMaxSize())
                }

                is AuthState.Authenticated -> {
                    LaunchedEffect(authState) {
                        navController.navigate(AppRootRoute.Home) {
                            popUpTo(AppRootRoute.Graph)
                        }
                    }
                }

                is AuthState.Unauthenticated, is AuthState.InvalidToken -> {
                    LaunchedEffect(authState) {
                        navController.navigate(AppRootRoute.Login) {
                            popUpTo(AppRootRoute.Graph)
                        }
                    }
                }
            }
        }
        authDestination(
            navController = navController,
            onAuthFinish = {
                navController.navigate(AppRootRoute.Home) {
                    popUpTo(Route.AuthGraph) {
                        inclusive = true
                    }
                }
            }
        )
        composable<AppRootRoute.Home> {
            val homeViewModel = koinViewModel<HomeViewModel>()
            HomeScreen(viewModel = homeViewModel)
        }
    }
}


//@Composable
//fun RootNavigation() {
//    val navController = rememberNavController()
//
//    val rootViewModel = koinViewModel<RootViewModel>()
//    val authState by rootViewModel.authState.collectAsState()
//
//    when (authState) {
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
