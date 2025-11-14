package nl.pcstet.navigation.main.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import nl.pcstet.navigation.main.data.network.ApiClientHolder
import nl.pcstet.navigation.ui.theme.NavigationTheme
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {
    private val apiClientHolder: ApiClientHolder by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NavigationTheme {
                val navController = rememberNavController()
                MainNavigation(navController)
            }
        }
    }
}