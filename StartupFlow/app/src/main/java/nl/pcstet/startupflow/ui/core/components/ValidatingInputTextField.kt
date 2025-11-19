package nl.pcstet.startupflow.ui.core.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import nl.pcstet.startupflow.ui.core.model.InputValidationState


@Composable
fun ValidatingInputTextField(
    input: String,
    onInputChange: (String) -> Unit,
    label: String,
    inputValidationState: InputValidationState,
    modifier: Modifier = Modifier,
) {
    OutlinedTextField(
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
