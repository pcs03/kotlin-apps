package nl.pcstet.navigation.navigation.presentation

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.Serializable
import nl.pcstet.navigation.auth.presentation.login.LoginScreen
import nl.pcstet.navigation.auth.presentation.login.LoginViewModel
import nl.pcstet.navigation.core.presentation.components.LoadingIndicator
import nl.pcstet.navigation.home.presentation.HomeScreen
import nl.pcstet.navigation.home.presentation.HomeViewModel
import org.koin.compose.viewmodel.koinViewModel

sealed interface Route {
    @Serializable
    data object AuthGraph : Route

    @Serializable
    data object LoginScreen : Route

    @Serializable
    data object MainGraph : Route

    @Serializable
    data object HomeScreen : Route
}

@Composable
fun RootNavigation() {
    val navController = rememberNavController()

    val rootViewModel = koinViewModel<RootViewModel>()
    val authState by rootViewModel.authState.collectAsState()

    when (authState) {
        RootViewModel.AuthState.LOADING -> {
            LoadingIndicator(Modifier.fillMaxSize())
        }

        else -> {
            val startDestination = if (authState == RootViewModel.AuthState.AUTHENTICATED) {
                Route.MainGraph
            } else {
                Route.AuthGraph
            }

            NavHost(
                navController = navController,
                startDestination = startDestination,
            ) {
                navigation<Route.AuthGraph>(
                    startDestination = Route.LoginScreen
                ) {
                    composable<Route.LoginScreen> {
                        val loginViewModel = koinViewModel<LoginViewModel>()
                        LoginScreen(viewModel = loginViewModel)
                    }
                }

                navigation<Route.MainGraph>(
                    startDestination = Route.HomeScreen
                ) {
                    composable<Route.HomeScreen> {
                        val homeViewModel = koinViewModel<HomeViewModel>()
                        HomeScreen(viewModel = homeViewModel)
                    }
                }
            }
        }
    }

}