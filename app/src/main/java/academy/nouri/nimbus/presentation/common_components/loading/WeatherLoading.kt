package academy.nouri.nimbus.presentation.common_components.loading

import academy.nouri.nimbus.presentation.common_components.loading.draw.drawCloud
import academy.nouri.nimbus.presentation.common_components.loading.draw.drawRainDrop
import academy.nouri.nimbus.presentation.common_components.loading.draw.drawSunWithRays
import academy.nouri.nimbus.presentation.theme.NimbusTheme
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun WeatherLoading(
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(
        label = "WeatherLoadingTransition"
    )

    //Init animation
    val sunRotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            tween(
                durationMillis = 8000,
                easing = LinearEasing
            )
        ),
        label = "SunAnimate"
    )
    val rayAlpha by infiniteTransition.animateFloat(
        initialValue = .4f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            tween(
                durationMillis = 1800,
                easing = FastOutLinearInEasing
            ),
            repeatMode = RepeatMode.Reverse
        ),
        label = "SunRayAnimate"
    )

    val cloudOffset by infiniteTransition.animateFloat(
        initialValue = -12f,
        targetValue = 12f,
        animationSpec = infiniteRepeatable(
            tween(
                durationMillis = 3000,
                easing = FastOutLinearInEasing,
            ),
            repeatMode = RepeatMode.Reverse
        ),
        label = "CloudAnimate"
    )

    val dropRain1 by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            tween(
                durationMillis = 1200,
                easing = LinearEasing
            )
        ),
        label = "DropRain1"
    )
    val dropRain2 by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            tween(
                durationMillis = 1200,
                delayMillis = 300,
                easing = LinearEasing
            )
        ),
        label = "DropRain2"
    )
    val dropRain3 by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            tween(
                durationMillis = 1200,
                delayMillis = 600,
                easing = LinearEasing
            )
        ),
        label = "DropRain3"
    )
    val dropRain4 by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            tween(
                durationMillis = 1200,
                delayMillis = 900,
                easing = LinearEasing
            )
        ),
        label = "DropRain4"
    )

    //Container
    Box(
        modifier = modifier
            .size(90.dp)
            .shadow(
                elevation = 35.dp,
                shape = CircleShape,
                ambientColor = Color(0xFF6A9FFF).copy(alpha = 0.6f),
                spotColor = Color(0xFF4A7FFF).copy(alpha = 0.8f)
            )
            .clip(CircleShape)
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color(0xFF1B2D4F),
                        Color(0xFF243B6E),
                        Color(0xFF1B2D4F)
                    )
                )
            )
        ,
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.size(60.dp)) {
            val cx = size.width / 2f
            val cy = size.height / 2f
            val sunX = cx - 25f
            val sunY = cy - 23f
            val sunR = 28f

            drawSunWithRays(
                center = Offset(sunX, sunY),
                sunX = sunX,
                sunY = sunY,
                radius = sunR,
                rotation = sunRotation,
                alpha = rayAlpha
            )

            drawCloud(
                centerX = cx + cloudOffset,
                centerY = cy,
                scale = 1f
            )

            drawRainDrop(
                centerX = cx,
                centerY = cy,
                drop1 = dropRain1,
                drop2 = dropRain2,
                drop3 = dropRain3,
                drop4 = dropRain4,
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun PreviewNimbusLogoAnimation() {
    NimbusTheme {
        WeatherLoading()
    }
}