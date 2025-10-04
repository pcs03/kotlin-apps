package nl.pcstet.romeplaces.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import nl.pcstet.romeplaces.R
import nl.pcstet.romeplaces.ui.theme.RomePlacesTheme

@Composable
fun RomePlacesApp() {
    val viewModel: RomePlacesViewModel = viewModel()
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = { RomePlacesTopBar() },
        bottomBar = { RomePLacesBottomBar() }
    ) { innerPadding ->
        RomePlacesHomeScreen(
            viewModel = viewModel,
            uiState = uiState,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RomePlacesTopBar() {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = stringResource(R.string.app_name),
                style = MaterialTheme.typography.displayLarge
            ) },
        navigationIcon = {

        }
    )
}

@Composable
fun RomePLacesBottomBar() {
    BottomAppBar {  }
}

@Preview
@Composable
fun RomePlacesAppLightThemePreview() {
    RomePlacesTheme(dynamicColor = false, darkTheme = false) {
        Surface {
            RomePlacesApp()
        }
    }
}

@Preview
@Composable
fun RomePlacesAppDarkThemePreview() {
    RomePlacesTheme(dynamicColor = false, darkTheme = true) {
        Surface {
            RomePlacesApp()
        }
    }
}
