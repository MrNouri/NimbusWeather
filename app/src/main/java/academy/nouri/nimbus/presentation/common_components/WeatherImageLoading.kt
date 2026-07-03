package academy.nouri.nimbus.presentation.common_components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import academy.nouri.nimbus.presentation.theme.NimbusTheme
import academy.nouri.nimbus.presentation.utils.previews.ComponentPreviews
import academy.nouri.nimbus.presentation.utils.previews.ThemePreviews
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.unit.dp

@Composable
fun WeatherImageLoading(
    modifier: Modifier = Modifier
) {
    val colorScheme = MaterialTheme.colorScheme
    val isDark = colorScheme.background.luminance() < .5f

    val surfaceColor = colorScheme.surfaceContainerHighest
    val borderColor = colorScheme.outlineVariant
    val primaryColor = colorScheme.primary
    val secondaryColor = colorScheme.secondary
    val imageTint = if (isDark) colorScheme.onSurface else colorScheme.onSurfaceVariant

    val transition = rememberInfiniteTransition(label = "WeatherImageLoading")

    val shimmerProgress by transition.animateFloat(
        initialValue = -1f,
        targetValue = 2f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1500,
                easing = LinearEasing
            )
        ),
        label = "ShimmerProgress"
    )

    val pulse by transition.animateFloat(
        initialValue = .72f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1100,
                easing = FastOutSlowInEasing
            ),
            repeatMode = RepeatMode.Reverse
        ),
        label = "Pulse"
    )

    val scanY by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1800,
                easing = FastOutSlowInEasing
            ),
            repeatMode = RepeatMode.Reverse
        ),
        label = "ScanLine"
    )

    Box(
        modifier = modifier.size(148.dp),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val width = size.width
            val height = size.height

            val cardWidth = width * .78f
            val cardHeight = height * .58f
            val left = (width - cardWidth) / 2f
            val top = height * .16f
            val radius = 24f

            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        primaryColor.copy(alpha = if (isDark) .22f else .16f),
                        secondaryColor.copy(alpha = if (isDark) .10f else .08f),
                        Color.Transparent
                    ),
                    center = Offset(width / 2f, height / 2f),
                    radius = width * .58f
                ),
                radius = width * .54f,
                center = Offset(width / 2f, height / 2f)
            )

            drawRoundRect(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        surfaceColor.copy(alpha = if (isDark) .72f else .92f),
                        surfaceColor.copy(alpha = if (isDark) .44f else .70f)
                    )
                ),
                topLeft = Offset(left, top),
                size = Size(cardWidth, cardHeight),
                cornerRadius = CornerRadius(radius, radius)
            )

            drawRoundRect(
                color = borderColor.copy(alpha = if (isDark) .42f else .78f),
                topLeft = Offset(left, top),
                size = Size(cardWidth, cardHeight),
                cornerRadius = CornerRadius(radius, radius),
                style = Stroke(width = 2.4f)
            )

            val shimmerWidth = cardWidth * .55f
            val shimmerStartX = left + (cardWidth + shimmerWidth) * shimmerProgress - shimmerWidth

            drawRoundRect(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color.Transparent,
                        colorScheme.onSurface.copy(alpha = if (isDark) .06f else .1f),
                        Color.Transparent
                    ),
                    start = Offset(shimmerStartX, top),
                    end = Offset(shimmerStartX + shimmerWidth, top + cardHeight)
                ),
                topLeft = Offset(left, top),
                size = Size(cardWidth, cardHeight),
                cornerRadius = CornerRadius(radius, radius)
            )

            drawCircle(
                color = colorScheme.tertiary.copy(alpha = .92f * pulse),
                radius = 9.5f,
                center = Offset(left + cardWidth - 27f, top + 25f)
            )

            val backMountain = Path().apply {
                moveTo(left + cardWidth * .42f, top + cardHeight - 18f)
                lineTo(left + cardWidth * .68f, top + cardHeight * .53f)
                lineTo(left + cardWidth - 16f, top + cardHeight - 18f)
                close()
            }

            val frontMountain = Path().apply {
                moveTo(left + 18f, top + cardHeight - 18f)
                lineTo(left + cardWidth * .36f, top + cardHeight * .45f)
                lineTo(left + cardWidth * .66f, top + cardHeight - 18f)
                close()
            }

            drawPath(
                path = backMountain,
                color = imageTint.copy(alpha = if (isDark) .30f else .24f)
            )

            drawPath(
                path = frontMountain,
                color = imageTint.copy(alpha = if (isDark) .58f else .42f)
            )

            val scanTop = top + 12f + scanY * (cardHeight - 24f)

            drawRoundRect(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        Color.Transparent,
                        primaryColor.copy(alpha = if (isDark) .58f else .46f),
                        Color.Transparent
                    )
                ),
                topLeft = Offset(left + 14f, scanTop),
                size = Size(cardWidth - 28f, 3f),
                cornerRadius = CornerRadius(4f, 4f)
            )

            val dotY = top + cardHeight + 24f
            repeat(3) { index ->
                val active = ((shimmerProgress + index * .28f) % 1f)
                drawCircle(
                    color = primaryColor.copy(alpha = .25f + active * .45f),
                    radius = 4.5f + active * 1.6f,
                    center = Offset(
                        x = width / 2f - 15f + index * 15f,
                        y = dotY
                    )
                )
            }

            drawRoundRect(
                color = colorScheme.onSurfaceVariant.copy(alpha = if (isDark) .20f else .16f),
                topLeft = Offset(left + cardWidth * .24f, dotY + 18f),
                size = Size(cardWidth * .52f, 4f),
                cornerRadius = CornerRadius(10f, 10f)
            )

            drawRoundRect(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        primaryColor.copy(alpha = .10f),
                        primaryColor.copy(alpha = .75f),
                        secondaryColor.copy(alpha = .55f)
                    )
                ),
                topLeft = Offset(left + cardWidth * .24f, dotY + 18f),
                size = Size(cardWidth * (.18f + .34f * pulse), 4f),
                cornerRadius = CornerRadius(10f, 10f)
            )
        }
    }
}

@ComponentPreviews
@Composable
fun WeatherImageLoadingPreview() {
    NimbusTheme {
        WeatherImageLoading()
    }
}