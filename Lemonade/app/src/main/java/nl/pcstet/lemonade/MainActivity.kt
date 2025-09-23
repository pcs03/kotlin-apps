package nl.pcstet.lemonade

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import nl.pcstet.lemonade.ui.theme.LemonadeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LemonadeTheme {
                LemonadeApp()
            }
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
            imageBackgroundColor = colorResource(R.color.image_background),
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
        Text(
            text = headerTitle,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            modifier = Modifier.padding(12.dp)
        )
    }
}

data class LemonState(val text: String, val image: Painter, val contentDescription: String, val pressAmount: Int = 1) {}

@Composable
fun LemonadeBody(
    imageBackgroundColor: Color,
    modifier: Modifier = Modifier
) {
    val lemonStates: List<LemonState> = listOf(
        LemonState(
            text = stringResource(R.string.lemon_tree),
            image = painterResource(R.drawable.lemon_tree),
            contentDescription = stringResource(R.string.lemon_tree_description),
        ),
        LemonState(
            text = stringResource(R.string.lemon_squeeze),
            image = painterResource(R.drawable.lemon_squeeze),
            contentDescription = stringResource(R.string.lemon_squeeze_description),
            pressAmount = (2..4).random(),
        ),
        LemonState(
            text = stringResource(R.string.lemon_drink),
            image = painterResource(R.drawable.lemon_drink),
            contentDescription = stringResource(R.string.lemon_drink_description),
        ),
        LemonState(
            text = stringResource(R.string.lemon_restart),
            image = painterResource(R.drawable.lemon_restart),
            contentDescription = stringResource(R.string.lemon_restart_description),
        ),
    )

    var lemonStateIndex by remember { mutableIntStateOf(0) }
    val lemonState = lemonStates[lemonStateIndex]
    var lemonPressAmount by remember { mutableIntStateOf(0)}

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
            onClick = {
                lemonPressAmount++

                if (lemonPressAmount == lemonState.pressAmount) {
                    lemonStateIndex = (lemonStateIndex + 1) % 4
                    lemonPressAmount = 0
                }
            }) {
            Image(
                painter = lemonState.image,
                contentDescription = lemonState.contentDescription,
            )
        }
        Text(
            text = lemonState.text,
            modifier = Modifier.padding(16.dp),
            fontSize = 12.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LemonadeAppPreview() {
    LemonadeTheme {
        LemonadeApp()
    }
}