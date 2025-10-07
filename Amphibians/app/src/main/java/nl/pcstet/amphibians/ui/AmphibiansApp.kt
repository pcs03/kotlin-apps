package nl.pcstet.amphibians.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import nl.pcstet.amphibians.ui.screens.AmphibianHomeScreen

@Composable
fun AmphibiansApp(
    modifier: Modifier = Modifier,
) {
    Scaffold(modifier = modifier) { innerPadding ->
        Surface(Modifier.padding(innerPadding).fillMaxSize()) {
            AmphibianHomeScreen()
        }
    }
}