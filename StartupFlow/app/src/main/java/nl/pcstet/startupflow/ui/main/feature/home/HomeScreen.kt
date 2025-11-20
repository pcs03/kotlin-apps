package nl.pcstet.startupflow.ui.main.feature.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun HomeScreen(
    state: HomeState,
    onLogoutButtonPressed: () -> Unit,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        HomeContent(
            state = state,
            onLogoutButtonPressed = onLogoutButtonPressed,
            modifier = Modifier.fillMaxSize().padding(innerPadding)
        )
    }
}

@Composable
fun HomeContent(
    state: HomeState,
    onLogoutButtonPressed: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        Text(text = state.message)
        TextButton(
            onClick = onLogoutButtonPressed
        ) {
            Text(text = "Logout")
        }
    }
}