package nl.pcstet.artspace

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import nl.pcstet.artspace.ui.theme.ArtSpaceTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ArtSpaceTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    ArtSpaceApp(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(
                                start = 8.dp,
                                top = innerPadding.calculateTopPadding(),
                                end = 8.dp,
                                bottom = innerPadding.calculateBottomPadding()
                            )

                    )
                }
            }
        }
    }
}

@Composable
fun ArtSpaceApp(modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally, modifier = modifier
    ) {
        ArtworkWall(
            image = R.drawable.pxl_20250818_142759383_preview, modifier = Modifier
                .weight(4f)
        )
        ArtworkDescriptor(
            artworkTitle = "Art",
            artworkArtist = "van Gogh",
            artworkYear = "6969",
            modifier = Modifier
                .weight(1f)
                .fillMaxSize()
        )
        DisplayController(
            onPreviousClick = {},
            onNextClick = {},
            modifier = Modifier
                .weight(1f)
                .fillMaxSize()
        )
    }
}

@Composable
fun ArtworkWall(
    @DrawableRes image: Int, modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier,
    ) {
        Image(
            painter = painterResource(image),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .size(320.dp)
                .align(Alignment.Center)
                .shadow(elevation = 4.dp)
                .padding(16.dp)
        )
    }
}

@Composable
fun ArtworkDescriptor(
    artworkTitle: String, artworkArtist: String, artworkYear: String, modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        Text(text = artworkTitle, fontWeight = FontWeight.Bold, fontSize = 32.sp)
        Text(text = "$artworkArtist ($artworkYear)")
    }
}

@Composable
fun DisplayController(
    onPreviousClick: () -> Unit, onNextClick: () -> Unit, modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier.padding(32.dp)
    ) {
        Button(onPreviousClick, modifier = Modifier.width(160.dp)) {
            Text(stringResource(R.string.button_previous))
        }
        Button(onNextClick, modifier = Modifier.width(160.dp)) {
            Text(stringResource(R.string.button_next))
        }
    }
}

@Composable
@Preview(showBackground = true)
fun ArtSpaceAppPreview() {
    ArtSpaceTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            ArtSpaceApp(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            )
        }
    }
}
