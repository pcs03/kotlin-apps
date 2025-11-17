package nl.pcstet.startupflow.ui.auth.feature.onboardinginput

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun OnboardingApiInputScreen(
    onNextClicked: () -> Unit,
    viewModel: OnboardingApiInputViewModel = koinViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    OnboardingApiInputScreenContent(
        uiState = uiState,
        onApiProtocolChange = viewModel::changeApiProtocol,
        onApiHostChange = viewModel::changeApiHost,
        onApiPathChange = viewModel::changeApiPath,
        onNextClicked = onNextClicked
    )
}

@Composable
private fun OnboardingApiInputScreenContent(
    uiState: ApiInputUiState,
    onApiProtocolChange: (String) -> Unit,
    onApiHostChange: (String) -> Unit,
    onApiPathChange: (String) -> Unit,
    onNextClicked: () -> Unit,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Enter API URL",
            style = MaterialTheme.typography.displayMedium
        )
        Spacer(modifier = Modifier.height(32.dp))

        SchemeSelector(
            selectedScheme = uiState.protocol,
            onSchemeChange = onApiProtocolChange
        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = uiState.host,
            onValueChange = onApiHostChange,
            label = { Text("API Host") },
//            placeholder = { Text("e.g. example.com") },
            isError = uiState.pathError != null,
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
        )
        if (uiState.hostError != null) {
            Text(
                text = uiState.hostError,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.fillMaxWidth()
            )
        }

        OutlinedTextField(
            value = uiState.path,
            onValueChange = onApiPathChange,
            label = { Text("API Path") },
//            placeholder = { Text("e.g. /api") },
            prefix = { Text("/") },
            isError = uiState.pathError != null,
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
        )
        if (uiState.pathError != null) {
            Text(
                text = uiState.pathError,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Button(
            onClick = onNextClicked,
            enabled = uiState.hostError == null && uiState.pathError == null
        ) {
            Text("Test API connection")
        }
    }
}

@Composable
private fun SchemeSelector(
    selectedScheme: String,
    onSchemeChange: (String) -> Unit,
) {
    val options = listOf("https", "http")
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        options.forEach { scheme ->
            Row(
                Modifier
                    .selectable(
                        selected = (scheme == selectedScheme),
                        onClick = { onSchemeChange(scheme) },
                        role = Role.RadioButton
                    )
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = (scheme == selectedScheme),
                    onClick = null // null recommended for radio buttons in 'selectable'
                )
                Text(
                    text = "$scheme://",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
        }
    }
}
