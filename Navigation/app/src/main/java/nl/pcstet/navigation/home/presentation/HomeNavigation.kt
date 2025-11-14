package nl.pcstet.navigation.home.presentation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import kotlinx.serialization.Serializable
import org.koin.compose.viewmodel.koinViewModel

sealed interface HomeRoute {
    @Serializable
    data object Graph : HomeRoute
    @Serializable
    data object Main : HomeRoute
}

fun NavGraphBuilder.homeDestination(
    navController: NavController,
    navigateToAppRoot: () -> Unit,
) {
    navigation<HomeRoute.Graph>(startDestination = HomeRoute.Main) {
        composable<HomeRoute.Main> {
            val homeViewModel = koinViewModel<HomeViewModel>()
            HomeScreen(
                onLogout = {
                    homeViewModel.logout()
                    navigateToAppRoot()
                }
            )
        }
    }
}