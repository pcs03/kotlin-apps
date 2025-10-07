package nl.pcstet.romeplaces.ui

import android.graphics.drawable.Icon
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import nl.pcstet.romeplaces.R
import nl.pcstet.romeplaces.model.RomePlace
import nl.pcstet.romeplaces.model.RomePlaceCategory
import nl.pcstet.romeplaces.ui.theme.RomePlacesTheme
import nl.pcstet.romeplaces.utils.RomePlacesContentType

@Composable
fun RomePlacesApp(
    windowSize: WindowWidthSizeClass
) {
    val viewModel: RomePlacesViewModel = viewModel()
    val uiState by viewModel.uiState.collectAsState()

    val contentType = when(windowSize) {
        WindowWidthSizeClass.Compact -> RomePlacesContentType.ListOnly
        WindowWidthSizeClass.Medium -> RomePlacesContentType.ListOnly
        WindowWidthSizeClass.Expanded -> RomePlacesContentType.ListAndDetail
        else -> RomePlacesContentType.ListOnly
    }

    Scaffold(
        topBar = { RomePlacesTopBar(
            title = uiState.currentCategory.title,
            showNavigationIcon = !uiState.isShowingListPage,
            onNavigationIconPressed = { viewModel.navigateToListPage() }
        ) },
        bottomBar = { RomePlacesBottomBar(
            uiState = uiState,
            onTabPressed = { romePlaceCategory ->
                viewModel.updateCurrentCategory(romePlaceCategory)
            },

        ) }
    ) { innerPadding ->
        RomePlacesHomeScreen(
            uiState = uiState,
            onListItemClick = { selectedRomePlace: RomePlace ->
                viewModel.updateCurrentRomePlace(
                    selectedRomePlace
                )
                viewModel.navigateToDetailPage()
            },
            onBackPressed = { viewModel.navigateToListPage() },
            contentType = contentType,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RomePlacesTopBar(
    @StringRes title: Int,
    showNavigationIcon: Boolean = false,
    onNavigationIconPressed: () -> Unit,
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = stringResource(title),
                style = MaterialTheme.typography.displayLarge
            )
        },
        navigationIcon = {
            if (showNavigationIcon) {
                IconButton(onClick = onNavigationIconPressed) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.navigate_back),
                        modifier = Modifier
                            .padding(dimensionResource(R.dimen.padding_small))
                            .size(dimensionResource(R.dimen.top_bar_nav_icon_size))
                    )
                }
            }
        },
    )
}

@Composable
fun RomePlacesBottomBar(
    uiState: RomePlacesUiState,
    onTabPressed: (RomePlaceCategory) -> Unit,
    modifier: Modifier = Modifier,
) {
    NavigationBar(
        modifier = modifier,
    ) {
        Spacer(Modifier.width(dimensionResource(R.dimen.padding_small)))
        for (category in RomePlaceCategory.entries) {
            NavigationBarItem(
                selected = category == uiState.currentCategory,
                onClick = { onTabPressed(category) },
                icon = {
                    Icon(
                        painter = painterResource(category.icon),
                        contentDescription = stringResource(category.title),
                        modifier = Modifier
                            .height(dimensionResource(R.dimen.navigation_icon_size))
                            .width(dimensionResource(R.dimen.navigation_icon_size))
                    )
                }
            )
        }
        Spacer(Modifier.width(dimensionResource(R.dimen.padding_small)))
    }
}

@Preview
@Composable
fun RomePlacesAppLightThemePreview() {
    RomePlacesTheme(dynamicColor = false, darkTheme = false) {
        Surface {
            RomePlacesApp(windowSize = WindowWidthSizeClass.Compact)
        }
    }
}

@Preview
@Composable
fun RomePlacesAppDarkThemePreview() {
    RomePlacesTheme(dynamicColor = false, darkTheme = true) {
        Surface {
            RomePlacesApp(windowSize = WindowWidthSizeClass.Compact)
        }
    }
}
