package nl.pcstet.lemonade

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import nl.pcstet.lemonade.ui.theme.LemonadeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LemonadeTheme {}
        }
    }
}

@Composable
fun LemonadeApp(modifier: Modifier = Modifier) {
    Column {
        LemonadeHeader(
            headerTitle = stringResource(R.string.app_name),
            modifier = modifier
                .weight(1f)
                .fillMaxSize()
        )
        LemonadeBody(
            image = painterResource(R.drawable.lemon_tree),
            imageDescription = stringResource(R.string.lemon_tree),
            imageBackgroundColor = colorResource(R.color.image_background),
            text = stringResource(R.string.lemon_tree),
            modifier = Modifier
                .weight(10f)
                .fillMaxSize()
        )
    }

}

@Composable
fun LemonadeHeader(headerTitle: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = colorResource(R.color.app_background)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom,
    ) {
        Text(text = headerTitle)
    }
}

@Composable
fun LemonadeBody(
    image: Painter,
    imageDescription: String,
    imageBackgroundColor: Color,
    text: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            modifier = Modifier
                .height(160.dp)
                .width(160.dp)
                .background(color = imageBackgroundColor, shape = RoundedCornerShape(32.dp)),
            colors = ButtonDefaults.buttonColors(containerColor = imageBackgroundColor),
            onClick = { /* TODO */ }) {
            Image(
                painter = image,
                contentDescription = imageDescription,
//                modifier = Modifier.align(Alignment.Center)
            )
        }
//        Box(
//            modifier = Modifier.
//            height(160.dp)
//                .width(160.dp)
//                .background(imageBackgroundColor)
//        ) {
//        }
        Text(text = text)
    }
}

@Preview(showBackground = true)
@Composable
fun LemonadeAppPreview() {
    LemonadeTheme {
        LemonadeApp()
    }
}