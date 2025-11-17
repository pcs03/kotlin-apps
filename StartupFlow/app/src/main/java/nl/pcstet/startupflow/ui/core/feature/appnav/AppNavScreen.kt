package nl.pcstet.startupflow.ui.core.feature.appnav

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import nl.pcstet.startupflow.data.auth.repository.model.AuthState
import nl.pcstet.startupflow.core.presentation.navigation.navigateToAuth
import nl.pcstet.startupflow.core.presentation.navigation.navigateToMain
import nl.pcstet.startupflow.core.presentation.navigation.navigateToOnboarding
import nl.pcstet.startupflow.ui.core.feature.splash.SplashRoute
import nl.pcstet.startupflow.ui.core.feature.splash.navigateToSplash
import nl.pcstet.startupflow.ui.core.feature.splash.splashDestination
import nl.pcstet.startupflow.ui.core.utils.toObjectNavigationRoute
import org.koin.compose.viewmodel.koinViewModel
import java.util.concurrent.atomic.AtomicReference

@Composable
fun AppNavScreen(
    viewModel: AppNavViewModel = koinViewModel(),
    navController: NavHostController = rememberNavController(),
) {
    val authState by viewModel.authState.collectAsStateWithLifecycle()
    val previousAuthStateReference = remember { AtomicReference(authState) }

    NavHost(
        navController = navController,
        startDestination = SplashRoute,
    ) {
        splashDestination()
    }

    val targetRoute = when (authState) {
        is AuthState.Loading -> SplashRoute
        AuthState.OnboardingRequired -> TODO()
        AuthState.Unauthenticated -> TODO()
        AuthState.Authenticated -> TODO()
    }
    val currentRoute = navController.currentDestination?.rootLevelRoute()

    if (currentRoute == targetRoute.toObjectNavigationRoute() && previousAuthStateReference.get() == authState) {
        previousAuthStateReference.set(authState)
        return
    }
    previousAuthStateReference.set(authState)

    val rootNavOptions = navOptions {
        popUpTo(navController.graph.id) {
            inclusive = false
        }
    }

    LaunchedEffect(authState) {
        when (val currentState = authState) {
            is AuthState.Authenticated -> {
                navController.navigateToMain() {
                    popUpTo(navController.graph.id) { inclusive = true }
                }
            }

            is AuthState.Unauthenticated -> {
                navController.navigateToAuth() {
                    popUpTo(navController.graph.id) { inclusive = true }
                }
            }

            is AuthState.OnboardingRequired -> {
                navController.navigateToOnboarding() {
                    popUpTo(navController.graph.id) { inclusive = true }
                }
            }

            is AuthState.Loading -> navController.navigateToSplash(rootNavOptions)
        }
    }
}

private fun NavDestination?.rootLevelRoute(): String? {
    if (this == null) {
        return null
    }
    if (parent?.route == null) {
        return route
    }
    return parent.rootLevelRoute()
}