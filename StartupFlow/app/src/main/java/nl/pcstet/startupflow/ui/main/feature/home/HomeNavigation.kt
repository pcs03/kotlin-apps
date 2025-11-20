package nl.pcstet.startupflow.ui.main.feature.home

import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import org.koin.compose.viewmodel.koinViewModel

@Serializable
data object HomeRoute

fun NavController.navigateToHome(navOptions: NavOptions? = null) {
    navigate(HomeRoute, navOptions = navOptions)
}

fun NavGraphBuilder.homeDestination() {
    composable<HomeRoute> {
        val viewModel: HomeViewModel = koinViewModel()
        val state by viewModel.stateFlow.collectAsStateWithLifecycle()

        HomeScreen(
            state = state,
            onLogoutButtonPressed = { viewModel.trySendAction(HomeAction.LogoutButtonPressed) }
        )
    }
}