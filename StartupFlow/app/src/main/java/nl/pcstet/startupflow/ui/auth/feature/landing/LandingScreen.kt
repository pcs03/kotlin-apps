package nl.pcstet.startupflow.ui.auth.feature.landing

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import nl.pcstet.startupflow.ui.core.base.utils.EventsEffect
import nl.pcstet.startupflow.ui.core.components.PasswordTextField
import nl.pcstet.startupflow.ui.core.components.ValidatingInputTextField
import nl.pcstet.startupflow.ui.core.model.InputValidationState
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun LandingScreen(
    state: LandingState,
    onEmailInputChanged: (String) -> Unit,
    onApiUrlInputChanged: (String) -> Unit,
    onPasswordInputChanged: (String) -> Unit,
    onRememberEmailToggle: (Boolean) -> Unit,
    onContinueClick: () -> Unit,
    snackbarHostState: SnackbarHostState,
) {
    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        when (state) {
            is LandingState.Loading -> CircularProgressIndicator()
            is LandingState.Content -> {
                LandingScreenContent(
                    state = state,
                    onEmailInputChanged = onEmailInputChanged,
                    onApiUrlInputChanged = onApiUrlInputChanged,
                    onPasswordInputChanged = onPasswordInputChanged,
                    onRememberEmailToggle = onRememberEmailToggle,
                    onContinueClick = onContinueClick,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                )
            }
        }
    }
}

@Composable
private fun LandingScreenContent(
    state: LandingState.Content,
    onEmailInputChanged: (String) -> Unit,
    onApiUrlInputChanged: (String) -> Unit,
    onPasswordInputChanged: (String) -> Unit,
    onRememberEmailToggle: (Boolean) -> Unit,
    onContinueClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Auth Landing",
            style = MaterialTheme.typography.displayMedium
        )
        Spacer(modifier = Modifier.height(32.dp))

        Text("Enter API URL")
        Spacer(modifier = Modifier.height(16.dp))

        ValidatingInputTextField(
            input = state.apiUrlInput,
            onInputChange = onApiUrlInputChanged,
            label = "API URL",
            inputValidationState = state.apiUrlValidation,
            modifier = Modifier.fillMaxWidth(),
        )

        ValidatingInputTextField(
            input = state.emailInput,
            onInputChange = onEmailInputChanged,
            label = "Email Address",
            inputValidationState = state.emailValidation,
            modifier = Modifier.fillMaxWidth(),
        )

        PasswordTextField(
            input = state.passwordInput,
            onInputChange = onPasswordInputChanged,
            modifier = Modifier.fillMaxWidth(),
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Remember Email?")
            Switch(
                checked = state.rememberEmailEnabled,
                onCheckedChange = onRememberEmailToggle,
            )
        }

        Button(
            onClick = onContinueClick,
            enabled = state.continueButtonEnabled
        ) {
            Text("Continue")
        }
    }
}