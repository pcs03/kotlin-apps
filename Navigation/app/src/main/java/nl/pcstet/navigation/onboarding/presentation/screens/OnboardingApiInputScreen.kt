package nl.pcstet.navigation.onboarding.presentation.screens

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import nl.pcstet.navigation.onboarding.presentation.ApiInputUiState

@Composable
fun OnboardingApiInputScreen(
    apiInputUiState: ApiInputUiState,
    onApiSchemeChange: (String) -> Unit,
    onApiHostChange: (String) -> Unit,
    onApiPathChange: (String) -> Unit,
    onNextClicked: () -> Unit,
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
            selectedScheme = apiInputUiState.scheme,
            onSchemeChange = onApiSchemeChange
        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = apiInputUiState.host,
            onValueChange = onApiHostChange,
            label = { Text("API Host") },
            prefix = { Text(text = "${apiInputUiState.scheme}://") },
            modifier = Modifier.fillMaxWidth(),
            isError = false,
            singleLine = true
        )

        OutlinedTextField(
            value = apiInputUiState.apiPath,
            onValueChange = onApiPathChange,
            label = { Text("API Path") },
            modifier = Modifier.fillMaxWidth(),
            isError = false,
            singleLine = true
        )

        if (apiInputUiState.validationError != null) {
            Text(
                text = apiInputUiState.validationError,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, top = 4.dp)
            )
        }

        Button(onClick = onNextClicked, enabled = apiInputUiState.isUrlValid) {
            Text("Test API connection")
        }
    }
}

@Composable
private fun SchemeSelector(
    selectedScheme: String,
    onSchemeChange: (String) -> Unit
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