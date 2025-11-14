package nl.pcstet.navigation.main.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import kotlinx.serialization.Serializable
import nl.pcstet.navigation.auth.presentation.AuthRoute
import nl.pcstet.navigation.auth.presentation.authDestination
import nl.pcstet.navigation.core.presentation.components.LoadingIndicator
import nl.pcstet.navigation.home.presentation.HomeRoute
import nl.pcstet.navigation.home.presentation.homeDestination
import nl.pcstet.navigation.main.presentation.utils.sharedViewModel

sealed interface AppRootRoute {
    @Serializable
    data object Graph : AppRootRoute

    @Serializable
    data object Loading : AppRootRoute
}

fun NavGraphBuilder.appRootDestination(
    navController: NavHostController,
    onOnboardingRequired: () -> Unit,
) {
    navigation<AppRootRoute.Graph>(startDestination = AppRootRoute.Loading) {
        composable<AppRootRoute.Loading> { backStackEntry ->
            val appRootViewModel = backStackEntry.sharedViewModel<RootViewModel>(navController)
            val mainViewModel = backStackEntry.sharedViewModel<MainViewModel>(navController)

            val appRootState by appRootViewModel.appRootState.collectAsState()

            when (appRootState) {
                is AppRootState.Loading -> {
                    LoadingIndicator(Modifier.fillMaxSize())
                }

                is AppRootState.OnboardingRequired -> {
                    LaunchedEffect(appRootState) {
                        onOnboardingRequired()
                    }
                }

                is AppRootState.Unauthenticated -> {
                    LaunchedEffect(appRootState) {
                        navController.navigate(AuthRoute.Graph) {
                            popUpTo(AppRootRoute.Graph)
                        }
                    }
                }

                is AppRootState.Authenticated -> {
                    LaunchedEffect(appRootState) {
                        val appRootState = appRootState as AppRootState.Authenticated
                        mainViewModel.initializeAuthenticatedApiClientHolder(
                            appRootState.backendUrl, appRootState.accessToken
                        )
                        navController.navigate(HomeRoute.Graph) {
                            popUpTo(AppRootRoute.Graph)
                        }
                    }
                }
            }
        }
        authDestination(
            navController = navController, onAuthFinish = {
                navController.navigate(AppRootRoute.Graph) {
                    popUpTo(AppRootRoute.Graph)
                }
            })
        homeDestination(
            navController = navController, navigateToAppRoot = {
                navController.navigate(AppRootRoute.Graph) {
                    popUpTo(AppRootRoute.Graph)
                }
            })
    }
}