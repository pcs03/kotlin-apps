package nl.pcstet.startupflow.ui.auth.feature.auth

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.navigation
import kotlinx.serialization.Serializable
import nl.pcstet.startupflow.ui.auth.feature.landing.LandingRoute
import nl.pcstet.startupflow.ui.auth.feature.landing.landingDestination
import nl.pcstet.startupflow.ui.auth.feature.landing.navigateToLanding
import nl.pcstet.startupflow.ui.auth.feature.welcome.welcomeDestination

@Serializable
data object AuthGraphRoute

fun NavController.navigateToAuthGraph(
    navOptions: NavOptions? = null
) {
    navigate(AuthGraphRoute, navOptions = navOptions)
}

fun NavGraphBuilder.authGraphDestination(
    navController: NavHostController,
) {
    navigation<AuthGraphRoute>(
        startDestination = LandingRoute
    ) {
        landingDestination(
            onNavigateToLogin = {},
//            onNavigateToLogin = { email ->
//                navController.navigateToLogin(
//                    email = email
//                )
//            }
        )
        welcomeDestination(
            onNavigateToLogin = { navController.navigateToLanding() }
        )
    }
}