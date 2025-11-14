package nl.pcstet.navigation.auth.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import kotlinx.serialization.Serializable
import nl.pcstet.navigation.auth.presentation.login.LoginScreen
import nl.pcstet.navigation.auth.presentation.login.LoginViewModel
import nl.pcstet.navigation.core.data.utils.AuthState
import nl.pcstet.navigation.core.presentation.components.LoadingIndicator
import nl.pcstet.navigation.home.presentation.HomeScreen
import nl.pcstet.navigation.home.presentation.HomeViewModel
import nl.pcstet.navigation.main.presentation.AppRootRoute
import nl.pcstet.navigation.main.presentation.RootViewModel
import org.koin.compose.viewmodel.koinViewModel

sealed interface AuthRoute {
    @Serializable
    data object Graph : AuthRoute

    //    @Serializable
//    data object Loading : AuthRoute
//
    @Serializable
    data object Login : AuthRoute
}

fun NavGraphBuilder.authDestination(
    navController: NavHostController,
    onAuthFinish: () -> Unit
) {
    navigation<AuthRoute.Graph>(startDestination = AuthRoute.Login) {
        composable<AuthRoute.Login> {
            val loginViewModel = koinViewModel<LoginViewModel>()
            LoginScreen(
                viewModel = loginViewModel,
                onLoginSuccess = onAuthFinish
            )
        }
    }
}


//@Composable
//fun RootNavigation() {
//    val navController = rememberNavController()
//
//    val rootViewModel = koinViewModel<RootViewModel>()
//    val authState by rootViewModel.authState.collectAsState()
//
//    when (authState) {
//        is AuthState.Loading, is AuthState.Unknown -> {
//            LoadingIndicator(Modifier.fillMaxSize())
//        }
//
//        is AuthState.Unauthenticated, is AuthState.Authenticated, is AuthState.InvalidToken -> {
//            val startDestination = if (authState is AuthState.Authenticated) {
//                Route.AppRootGraph
//            } else {
//                Route.AuthGraph
//            }
//
//            NavHost(
//                navController = navController,
//                startDestination = startDestination,
//            ) {
//                navigation<Route.AuthGraph>(
//                    startDestination = Route.LoginScreen
//                ) {
//                    composable<Route.LoginScreen> {
//                        val loginViewModel = koinViewModel<LoginViewModel>()
//                        LoginScreen(viewModel = loginViewModel)
//                    }
//                }
//
//                navigation<Route.AppRootGraph>(
//                    startDestination = Route.HomeScreen
//                ) {
//                    composable<Route.HomeScreen> {
//                        val homeViewModel = koinViewModel<HomeViewModel>()
//                        HomeScreen(viewModel = homeViewModel)
//                    }
//                }
//            }
//        }
//    }
//
//}
