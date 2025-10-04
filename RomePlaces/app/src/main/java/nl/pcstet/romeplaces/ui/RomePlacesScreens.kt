package nl.pcstet.romeplaces.ui

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import nl.pcstet.romeplaces.LocalRomePlacesDataProvider
import nl.pcstet.romeplaces.model.RomePlace
import nl.pcstet.romeplaces.ui.theme.RomePlacesTheme

@Composable
fun RomePlacesHomeScreen(
    viewModel: RomePlacesViewModel,
    uiState: RomePlacesUiState,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        RomePlacesList(
            romePlaces = uiState.romePlacesList,
            onItemClick = { selectedRomePlace: RomePlace ->
                viewModel.updateCurrentRomePlace(
                    selectedRomePlace
                )
            }
        )
    }
}

@Composable
private fun RomePlacesList(
    romePlaces: List<RomePlace>,
    onItemClick: (RomePlace) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(romePlaces) { romePlace ->
            RomePlacesCard(
                romePlace = romePlace,
                onItemClick = onItemClick,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun RomePlacesCard(
    romePlace: RomePlace,
    onItemClick: (RomePlace) -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        elevation = CardDefaults.cardElevation(),
        modifier = modifier,
        shape = RoundedCornerShape(20.dp),
        onClick = { onItemClick(romePlace) }) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .size(128.dp)
        ) {
            RomePlacesCardImage(
                imageResourceId = romePlace.imageResourceId,
                modifier = Modifier.size(128.dp)
            )
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = stringResource(romePlace.titleResourceId),
                    style = MaterialTheme.typography.displayMedium
                )
                Text(
                    text = stringResource(romePlace.descriptionResourceId),
                    style = MaterialTheme.typography.bodyLarge,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 3
                )
                Spacer(modifier = modifier.weight(1f))
                Text(
                    text = "Section placeholder",
                    style = MaterialTheme.typography.labelSmall,
                    modifier = Modifier.align(Alignment.End)
                )
            }
        }
    }
}

@Composable
private fun RomePlacesCardImage(@DrawableRes imageResourceId: Int, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
    ) {
        Image(
            painter = painterResource(imageResourceId),
            contentDescription = null,
            alignment = Alignment.Center,
            contentScale = ContentScale.Crop
        )
    }
}

@Preview
@Composable
fun RomePlacesListPreview() {
    RomePlacesTheme {
        RomePlacesList(
            romePlaces = LocalRomePlacesDataProvider.getRomePlacesData(),
            onItemClick = {},
        )
    }
}

@Preview
@Composable
fun RomePlacesCardPreview() {
    RomePlacesTheme {
        RomePlacesCard(
            romePlace = LocalRomePlacesDataProvider.defaultRomePlace,
            onItemClick = {},
        )
    }
}