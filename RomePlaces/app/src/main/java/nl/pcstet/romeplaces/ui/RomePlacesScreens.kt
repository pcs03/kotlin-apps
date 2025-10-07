package nl.pcstet.romeplaces.ui

import androidx.activity.compose.BackHandler
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import nl.pcstet.romeplaces.R
import nl.pcstet.romeplaces.data.LocalRomePlacesDataProvider
import nl.pcstet.romeplaces.model.RomePlace
import nl.pcstet.romeplaces.ui.theme.RomePlacesTheme
import nl.pcstet.romeplaces.utils.RomePlacesContentType

@Composable
fun RomePlacesHomeScreen(
    uiState: RomePlacesUiState,
    onListItemClick: (RomePlace) -> Unit,
    onBackPressed: () -> Unit,
    contentType: RomePlacesContentType,
    modifier: Modifier = Modifier,
) {
    if (contentType == RomePlacesContentType.ListAndDetail) {
        RomePlacesListAndDetail(
            romePlaces = uiState.currentCategoryPlaces,
            selectedRomePlace = uiState.currentRomePlace,
            onListItemClick = onListItemClick,
            onBackPressed = onBackPressed,
            modifier = modifier
        )
    } else {
        if (uiState.isShowingListPage) {
            RomePlacesList(
                romePlaces = uiState.currentCategoryPlaces,
                onListItemClick = onListItemClick,
                modifier = modifier
            )
        } else {
            RomePlacesDetail(
                romePlace = uiState.currentRomePlace,
                onBackPressed = onBackPressed,
                modifier = modifier
            )
        }
    }
}

@Composable
private fun RomePlacesListAndDetail(
    romePlaces: List<RomePlace>,
    selectedRomePlace: RomePlace,
    onListItemClick: (RomePlace) -> Unit,
    onBackPressed: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(modifier) {
        RomePlacesList(
            romePlaces = romePlaces,
            onListItemClick = onListItemClick,
            modifier = Modifier
                .weight(1f)
                .fillMaxSize()
        )
        RomePlacesDetail(
            romePlace = selectedRomePlace,
            onBackPressed = onBackPressed,
            modifier = Modifier
                .weight(1f)
                .fillMaxSize()
        )
    }
}

@Composable
private fun RomePlacesList(
    romePlaces: List<RomePlace>,
    onListItemClick: (RomePlace) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier.padding(horizontal = dimensionResource(R.dimen.rome_places_list_horizontal_padding)),
        verticalArrangement = Arrangement.spacedBy(space = dimensionResource(R.dimen.rome_places_list_spacing)),
    ) {
        items(romePlaces, key = { romePlace -> romePlace.id }) { romePlace ->
            RomePlacesCard(
                romePlace = romePlace,
                onItemClick = onListItemClick,
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
                modifier = Modifier.size(dimensionResource(R.dimen.card_image_height))
            )
            Spacer(Modifier.width(dimensionResource(R.dimen.padding_small)))
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = stringResource(romePlace.titleResourceId),
                    style = MaterialTheme.typography.displayMedium,
                    modifier = modifier.padding(vertical = dimensionResource(R.dimen.padding_small))
                )
                Text(
                    text = stringResource(romePlace.descriptionResourceId),
                    style = MaterialTheme.typography.bodyLarge,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 3
                )
                Spacer(modifier = modifier.weight(1f))
                Text(
                    text = stringResource(romePlace.citySection.title),
                    style = MaterialTheme.typography.labelSmall,
                    modifier = Modifier
                        .padding(
                            end = dimensionResource(R.dimen.padding_small),
                            bottom = dimensionResource(R.dimen.padding_small)
                        )
                        .align(Alignment.End)
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
            contentScale = ContentScale.Crop,
            alignment = Alignment.Center,
            modifier = modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun RomePlacesDetail(
    romePlace: RomePlace,
    onBackPressed: () -> Unit,
    modifier: Modifier = Modifier,
) {
    BackHandler {
        onBackPressed()
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.verticalScroll(rememberScrollState())
    ) {
        RomeDetailImage(
            imageResourceId = romePlace.imageResourceId,
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(Modifier.height(dimensionResource(R.dimen.detail_items_spacing)))
        Text(
            text = stringResource(romePlace.titleResourceId),
            style = MaterialTheme.typography.displayMedium
        )
        Text(
            text = "${stringResource(romePlace.category.title)} - ${stringResource(romePlace.citySection.title)}",
            style = MaterialTheme.typography.labelSmall
        )
        Spacer(Modifier.height(dimensionResource(R.dimen.detail_items_spacing)))
        Card(
            shape = RoundedCornerShape(
                topStart = 16.dp,
                topEnd = 16.dp,
                bottomStart = 0.dp,
                bottomEnd = 0.dp
            ),
            modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.padding_small))
        ) {
            Text(
                text = stringResource(R.string.place_description),
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Justify,
                modifier = Modifier
                    .padding(dimensionResource(R.dimen.padding_small))
            )
        }
    }
}

@Composable
private fun RomeDetailImage(@DrawableRes imageResourceId: Int, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(
            topStart = 0.dp,
            topEnd = 0.dp,
            bottomStart = 16.dp,
            bottomEnd = 16.dp
        )
    ) {
        Image(
            painter = painterResource(imageResourceId),
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            alignment = Alignment.Center,
            modifier = modifier.fillMaxWidth()
        )
    }
}

@Preview
@Composable
fun RomePlacesDetailPreview() {
    RomePlacesTheme(dynamicColor = false) {
        Surface {
            RomePlacesDetail(
                romePlace = LocalRomePlacesDataProvider.defaultRomePlace, onBackPressed = {})
        }
    }
}

@Preview
@Composable
fun RomePlacesDetailDarkThemePreview() {
    RomePlacesTheme(dynamicColor = false, darkTheme = true) {
        Surface {
            RomePlacesDetail(
                romePlace = LocalRomePlacesDataProvider.defaultRomePlace, onBackPressed = {})
        }
    }
}

@Preview
@Composable
fun RomePlacesListPreview() {
    RomePlacesTheme {
        RomePlacesList(
            romePlaces = LocalRomePlacesDataProvider.getRomePlacesData(),
            onListItemClick = {},
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