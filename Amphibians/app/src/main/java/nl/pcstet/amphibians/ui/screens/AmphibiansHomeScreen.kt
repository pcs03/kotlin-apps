package nl.pcstet.amphibians.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import nl.pcstet.amphibians.R
import nl.pcstet.amphibians.model.Amphibian

@Composable
fun AmphibianHomeScreen(
    modifier: Modifier = Modifier,
) {
    val viewModel: AmphibiansViewModel = viewModel(factory = AmphibiansViewModel.Factory)
    val amphibianUiState = viewModel.amphibianUiState

    when (amphibianUiState) {
        is AmphibianUiState.Success -> AmphibiansList(
            amphibianUiState.amphibians,
            modifier = modifier
        )

        is AmphibianUiState.Error -> ErrorScreen(
            onRetryClick = viewModel::getAmphibians,
            modifier = modifier.fillMaxSize()
        )

        is AmphibianUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
    }
}

@Composable
fun AmphibiansList(
    amphibians: List<Amphibian>,
    modifier: Modifier = Modifier,
) {
    LazyColumn(modifier) {
        items(items = amphibians, key = { amphibian -> amphibian.name }) { amphibian ->
            AmphibianCard(
                amphibian = amphibian,
                modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )
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
        shape = MaterialTheme.shapes.medium,
        modifier = modifier
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(
                text = "${amphibian.name} (${amphibian.type})",
                style = MaterialTheme.typography.titleLarge
            )
            AmphibianImage(
                amphibian = amphibian,
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .fillMaxWidth()
            )
            Text(text = amphibian.description, style = MaterialTheme.typography.bodyLarge)
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
        error = painterResource(R.drawable.ic_broken_image),
        placeholder = painterResource(R.drawable.loading_img),
        contentDescription = amphibian.name,
        contentScale = ContentScale.Crop,
        modifier = modifier
    )
}

@Composable
fun ErrorScreen(
    onRetryClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_connection_error),
            contentDescription = null,
            modifier = Modifier.size(256.dp),
            tint = MaterialTheme.colorScheme.outline
        )
        Text(text = stringResource(R.string.connection_error))
        Button(onClick = onRetryClick) {
            Text(stringResource(R.string.retry))
        }
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        Icon(
            painter = painterResource(R.drawable.loading_img),
            contentDescription = null,
            modifier = Modifier.size(256.dp),
            tint = MaterialTheme.colorScheme.outline
        )
    }
}
