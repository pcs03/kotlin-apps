package nl.pcstet.artspace

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
                            .padding(innerPadding)
                    )
                }
            }
        }
    }
}

data class Artwork(
    @DrawableRes val imageResource: Int, val title: String, val artist: String, val year: String
)

val artworkAlbum = listOf(
    Artwork(
        imageResource = R.drawable.suzy1,
        title = "A cute kitty enjoying the weather",
        artist = "Van Gogh",
        year = "2000"
    ),
    Artwork(
        imageResource = R.drawable.suzy2,
        title = "A cute kitty staring at an intruder",
        artist = "Van Gogh",
        year = "2000"
    ),
    Artwork(
        imageResource = R.drawable.suzy3,
        title = "A cute kitty being extremely lazy in bed",
        artist = "Van Gogh",
        year = "2000"
    ),
    Artwork(
        imageResource = R.drawable.whiskey,
        title = "A gorgeous bottle of whiskey",
        artist = "Van Gogh",
        year = "2000"
    ),
    Artwork(
        imageResource = R.drawable.maitai,
        title = "A beautiful Mai-Tai",
        artist = "Van Gogh",
        year = "2000"
    ),
    Artwork(
        imageResource = R.drawable.mint,
        title = "Indoor mint plants",
        artist = "Van Gogh",
        year = "2000"
    ),
    Artwork(
        imageResource = R.drawable.tiger,
        title = "The stuff that makes Wehraboos wet",
        artist = "Van Gogh",
        year = "2000"
    ),
    Artwork(
        imageResource = R.drawable.tiramisu,
        title = "Homemade Tiramisu",
        artist = "Van Gogh",
        year = "2000"
    ),
)

fun mod(a: Int, b: Int): Int {
    return ((a % b) + b) % b
}

fun getNewAlbumIndex(changeIndexBy: Int, albumIndex: Int, albumLength: Int): Int {
    return mod(albumIndex + changeIndexBy, albumLength)
}

fun previousAlbumIndex(albumIndex: Int, albumLength: Int): Int {
    return getNewAlbumIndex(-1, albumIndex, albumLength)
}

fun nextAlbumIndex(albumIndex: Int, albumLength: Int): Int {
    return getNewAlbumIndex(1, albumIndex, albumLength)
}

@Composable
fun ArtSpaceApp(modifier: Modifier = Modifier) {
    var artworkIndex by remember { mutableIntStateOf(0) }
    val artwork = artworkAlbum[artworkIndex]

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.padding(horizontal = 16.dp)
    ) {
        ArtworkWall(
            image = artwork.imageResource, modifier = Modifier
                .weight(6f)
                .fillMaxSize()
        )
        ArtworkDescriptor(
            artworkTitle = artwork.title,
            artworkArtist = artwork.artist,
            artworkYear = artwork.year,
            modifier = Modifier
                .weight(3f)
                .fillMaxSize()
        )
        DisplayController(
            onPreviousClick = {
                artworkIndex = previousAlbumIndex(
                    albumIndex = artworkIndex,
                    albumLength = artworkAlbum.count()
                )
            },
            onNextClick = {
                artworkIndex = nextAlbumIndex(
                    albumIndex = artworkIndex,
                    albumLength = artworkAlbum.count()
                )
            },
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
    Box(modifier) {
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .align(Alignment.Center)
                .wrapContentSize()
                .background(colorResource(R.color.artwork_info_background))
                .padding(16.dp)
        ) {
            Text(
                text = artworkTitle,
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.Light,
                fontSize = 32.sp,
                lineHeight = 32.sp,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Row(
                modifier = Modifier
            ) {
                Text(artworkArtist, fontWeight = FontWeight.Bold)
                Text(" ($artworkYear)", fontWeight = FontWeight.Light)
            }
        }
    }
}

@Composable
fun DisplayController(
    onPreviousClick: () -> Unit, onNextClick: () -> Unit, modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
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
