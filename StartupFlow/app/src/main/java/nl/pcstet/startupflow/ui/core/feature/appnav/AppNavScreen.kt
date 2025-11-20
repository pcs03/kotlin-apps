package nl.pcstet.startupflow.ui.core.feature.appnav

import android.util.Log
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
import nl.pcstet.startupflow.ui.auth.feature.auth.AuthGraphRoute
import nl.pcstet.startupflow.ui.auth.feature.auth.authGraphDestination
import nl.pcstet.startupflow.ui.auth.feature.auth.navigateToAuthGraph
import nl.pcstet.startupflow.ui.auth.feature.welcome.navigateToWelcome
import nl.pcstet.startupflow.ui.main.feature.main.MainGraphRoute
import nl.pcstet.startupflow.ui.main.feature.main.mainDestination
import nl.pcstet.startupflow.ui.main.feature.main.navigateToMainGraph
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

    Log.d("AppNavScreen", state.toString())

    NavHost(
        navController = navController,
        startDestination = SplashRoute,
    ) {
        splashDestination()
        authGraphDestination(navController)
        mainDestination(navController)
    }

    val targetRoute = when (state) {
        is AppNavState.Splash -> SplashRoute
        is AppNavState.LoggedIn -> MainGraphRoute
        is AppNavState.Auth, is AppNavState.AuthWithWelcome -> AuthGraphRoute
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
        Log.d("AppNavScreen", state.toString())
        when (val currentState = state) {
            is AppNavState.Splash -> navController.navigateToSplash(rootNavOptions)
            is AppNavState.Auth -> navController.navigateToAuthGraph(rootNavOptions)
            is AppNavState.AuthWithWelcome -> navController.navigateToWelcome(rootNavOptions)
            is AppNavState.LoggedIn -> { navController.navigateToMainGraph(rootNavOptions) }
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