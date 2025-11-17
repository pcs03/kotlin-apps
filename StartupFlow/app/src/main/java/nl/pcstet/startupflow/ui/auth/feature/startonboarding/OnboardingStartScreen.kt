package nl.pcstet.startupflow.ui.auth.feature.startonboarding

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun OnboardingStartScreen(
    onNavigateToApiInput: () -> Unit
) {
    Log.d("OnboardingWelcomeScreen", "Instantiation")
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Welcome",
            style = MaterialTheme.typography.displayMedium
        )
        Spacer(modifier = Modifier.height(32.dp))
        Button(onClick = onNavigateToApiInput) {
            Text("Next")
        }
    }
}
