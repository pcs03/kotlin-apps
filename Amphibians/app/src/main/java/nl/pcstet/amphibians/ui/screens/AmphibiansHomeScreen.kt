package nl.pcstet.amphibians.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import nl.pcstet.amphibians.model.Amphibian

@Composable
fun AmphibianHomeScreen(
    modifier: Modifier = Modifier,
) {
    val viewModel: AmphibiansViewModel = viewModel()
    val amphibianUiState = viewModel.amphibianUiState

    when (amphibianUiState) {
        is AmphibianUiState.Success -> AmphibiansList(amphibianUiState.amphibians, modifier = modifier)
        is AmphibianUiState.Error -> ErrorScreen(modifier = modifier)
        is AmphibianUiState.Loading -> LoadingScreen(modifier = modifier)
    }
}

@Composable
fun AmphibiansList(
    amphibians: List<Amphibian>,
    modifier: Modifier = Modifier,
) {
    LazyColumn(modifier) {
        items(items = amphibians, key = { amphibian -> amphibian.name }) { amphibian ->
            AmphibianCard(amphibian = amphibian)
        }
    }
}

@Composable
fun AmphibianCard(
    amphibian: Amphibian,
    modifier: Modifier = Modifier,
) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = modifier
    ) {
        Column {
            Text(text = "${amphibian.name} (${amphibian.type})")
            AmphibianImage(amphibian)
            Text(text = amphibian.description)
        }
    }
}

@Composable
fun AmphibianImage(
    amphibian: Amphibian,
    modifier: Modifier = Modifier
) {
    AsyncImage(
        model = ImageRequest.Builder(context = LocalContext.current)
            .data(amphibian.imageSource)
            .crossfade(true)
            .build(),
        contentDescription = amphibian.name,
        contentScale = ContentScale.Crop,
        modifier = modifier.fillMaxWidth()
    )
}

@Composable
fun ErrorScreen(modifier: Modifier = Modifier) {
    Text(text = "Error")
}
@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Text(text = "Loading")
}
