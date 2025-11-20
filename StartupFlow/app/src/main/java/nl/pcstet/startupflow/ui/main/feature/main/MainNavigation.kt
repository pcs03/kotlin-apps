package nl.pcstet.startupflow.ui.main.feature.main

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.navigation
import kotlinx.serialization.Serializable
import nl.pcstet.startupflow.ui.main.feature.home.HomeRoute
import nl.pcstet.startupflow.ui.main.feature.home.homeDestination

@Serializable
data object MainGraphRoute

fun NavController.navigateToMainGraph(navOptions: NavOptions? = null) {
    navigate(MainGraphRoute, navOptions = navOptions)
}

fun NavGraphBuilder.mainDestination(
    navController: NavController,
) {
    navigation<MainGraphRoute>(
        startDestination = HomeRoute
    ) {
        homeDestination()
    }

}