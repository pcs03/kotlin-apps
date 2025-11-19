package nl.pcstet.startupflow.ui.core.feature.appnav

import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import org.koin.compose.viewmodel.koinViewModel

@Serializable
data object AppNavigationRoute

fun NavGraphBuilder.appNavDestination() {
    composable<AppNavigationRoute> {
        AppNavScreen()
    }
}