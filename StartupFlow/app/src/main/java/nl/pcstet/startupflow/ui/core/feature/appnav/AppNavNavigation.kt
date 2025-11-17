package nl.pcstet.startupflow.ui.core.feature.appnav

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object AppNavigationRoute

fun NavGraphBuilder.appNavDestination() {
    composable<AppNavigationRoute> {
        AppNavScreen()
    }
}