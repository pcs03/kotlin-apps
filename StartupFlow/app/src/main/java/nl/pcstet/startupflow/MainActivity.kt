package nl.pcstet.startupflow

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import nl.pcstet.startupflow.ui.core.feature.appnav.AppNavScreen
import nl.pcstet.startupflow.ui.theme.StartupFlowTheme

class MainActivity : ComponentActivity() {
    override fun onDestroy() {
        super.onDestroy()
        Log.d("MainActivity", "onDestroy")
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("MainActivity", "onCreate")
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StartupFlowTheme {
                AppNavScreen()
            }
        }
    }
}