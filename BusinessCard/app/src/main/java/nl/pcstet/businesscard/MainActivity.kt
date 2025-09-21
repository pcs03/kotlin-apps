package nl.pcstet.businesscard

import android.graphics.Paint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Phone
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import nl.pcstet.businesscard.ui.theme.BusinessCardTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BusinessCardTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    BusinessCard()
                }
            }
        }
    }
}

@Composable
fun NameCard(
    image: Painter,
    name: String,
    title: String,
    backgroundColor: Color,
    modifier: Modifier = Modifier
) {
    Column(modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .background(backgroundColor)
                .height(100.dp)
                .width(100.dp)
        ) {
            Image(
                painter = image,
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(4.dp)
            )
        }
        Text(
            text = name,
            modifier = Modifier.padding(2.dp),
            fontSize = 50.sp,
            fontWeight = FontWeight.Light,
        )
        Text(
            text = title,
            modifier = Modifier.padding(2.dp),
            fontWeight = FontWeight.Bold,
            color = colorResource(R.color.textGreen)
        )
    }
}

data class IconTextResources(val icon: ImageVector, val text: String)

@Composable
fun IconText(icon: ImageVector, text: String, modifier: Modifier = Modifier) {
    Row(modifier) {
        Icon(imageVector = icon, contentDescription = null,
            tint = colorResource(R.color.textGreen),
            modifier = Modifier.padding(end = 16.dp))
        Text(text = text)
    }
}

@Composable
fun InfoCard(infoFields: List<IconTextResources>, modifier: Modifier = Modifier) {
    Column(modifier) {
        infoFields.forEach { pair ->
            IconText(pair.icon, pair.text, modifier = Modifier.padding(2.dp))
        }
    }
}

@Composable
fun BusinessCard(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = colorResource(R.color.background))
    ) {
        NameCard(
            modifier = Modifier.align(Alignment.Center),
            image = painterResource(R.drawable.android_logo),
            backgroundColor = colorResource(R.color.imageBackground),
            name = stringResource(R.string.name),
            title = stringResource(R.string.title),
        )
        InfoCard(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp),
            infoFields = listOf(
                IconTextResources(Icons.Rounded.Phone, stringResource(R.string.phone)),
                IconTextResources(Icons.Rounded.Share, stringResource(R.string.social)),
                IconTextResources(Icons.Rounded.Email, stringResource(R.string.email)),
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BusinessCardPreview() {
    BusinessCardTheme {
        BusinessCard()
    }
}