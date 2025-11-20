package nl.pcstet.startupflow.ui.core.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import nl.pcstet.startupflow.ui.core.model.InputValidationState


@Composable
fun ValidatingInputTextField(
    input: String,
    onInputChange: (String) -> Unit,
    label: String,
    inputValidationState: InputValidationState,
    modifier: Modifier = Modifier,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
) {
    TextField(
        modifier = modifier,
        value = input,
        onValueChange = onInputChange,
        label = { Text(label) },
        isError = inputValidationState is InputValidationState.Invalid,
        trailingIcon = {
            Box {
                when (inputValidationState) {
                    is InputValidationState.Idle -> {}
                    is InputValidationState.Loading -> CircularProgressIndicator(Modifier.size(28.dp))
                    is InputValidationState.Valid -> Icon(
                        Icons.Filled.Check,
                        contentDescription = null
                    )

                    is InputValidationState.Invalid -> Icon(
                        Icons.Filled.Error,
                        contentDescription = null
                    )
                }
            }
        },
        supportingText = {
            if (inputValidationState is InputValidationState.Invalid) {
                Text(text = inputValidationState.message)
            }
        }
    )
}

@Composable
fun PasswordTextField(
    input: String,
    onInputChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    TextField(
        modifier = modifier,
        value = input,
        onValueChange = onInputChange,
        label = { Text("Password") },
        singleLine = true,
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        trailingIcon = {
            val icon = if(passwordVisible) {
                Icons.Filled.Visibility
            } else {
                Icons.Filled.VisibilityOff
            }

            val contentDescription = if(passwordVisible) "Hide password" else "Show password"

            IconButton(onClick = {passwordVisible = !passwordVisible}) {
                Icon(imageVector = icon, contentDescription = contentDescription)
            }
        }
    )
}