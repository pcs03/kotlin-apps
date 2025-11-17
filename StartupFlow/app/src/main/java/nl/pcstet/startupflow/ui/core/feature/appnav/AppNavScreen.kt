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
    val state by viewModel.stateFlow.collectAsStateWithLifecycle()
    val previousAuthStateReference = remember { AtomicReference(state) }

    NavHost(
        navController = navController,
        startDestination = SplashRoute,
    ) {
        splashDestination()
    }

    val targetRoute = when (state) {
        is AppNavState.Splash -> SplashRoute
        AppNavState.AppLocked -> TODO()
        AppNavState.AppUnlocked -> TODO()
        AppNavState.Auth -> TODO()
        AppNavState.AuthWithWelcome -> TODO()
    }
    val currentRoute = navController.currentDestination?.rootLevelRoute()

    if (currentRoute == targetRoute.toObjectNavigationRoute() && previousAuthStateReference.get() == state) {
        previousAuthStateReference.set(state)
        return
    }
    previousAuthStateReference.set(state)

    val rootNavOptions = navOptions {
        popUpTo(navController.graph.id) {
            inclusive = false
        }
    }

    LaunchedEffect(state) {
        when (val currentState = state) {
            is AppNavState.Splash -> navController.navigateToSplash(rootNavOptions)
//            is AppNavState.Auth -> navController.navigateTO
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