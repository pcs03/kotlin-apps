package nl.pcstet.navigation.main.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import nl.pcstet.navigation.core.data.network.ApiClientHolder
import nl.pcstet.navigation.core.presentation.components.LoadingIndicator
import nl.pcstet.navigation.onboarding.presentation.OnboardingNavigation
import nl.pcstet.navigation.ui.theme.NavigationTheme
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel

class MainActivity : ComponentActivity() {
    private val mainViewModel: MainViewModel by viewModel()
    private val apiClientHolder: ApiClientHolder by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NavigationTheme {
                val mainViewModelState by mainViewModel.mainViewModelState.collectAsState()

                when (mainViewModelState) {
                    is MainViewModelState.Loading -> LoadingIndicator(Modifier.fillMaxSize())
                    is MainViewModelState.OnboardingRequired -> OnboardingNavigation(Modifier.fillMaxSize())
                    is MainViewModelState.OnboardingComplete -> {
                        val backendUrl =
                            (mainViewModelState as MainViewModelState.OnboardingComplete).backendUrl
                        apiClientHolder.initialize(baseUrl = backendUrl)

                        Log.d("MainActivity", apiClientHolder.getClient().engineConfig.toString())

                        RootNavigation()
                    }
                }
            }
        }
    }
}