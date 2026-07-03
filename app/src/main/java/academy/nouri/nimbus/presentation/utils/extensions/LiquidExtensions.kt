package academy.nouri.nimbus.presentation.utils.extensions

import androidx.compose.foundation.border
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.HazeTint

fun enableHazeEffect(
    blurRadius: Dp,
    colorAlpha: Float
): HazeStyle {
    return HazeStyle(
        blurRadius = blurRadius,
        tints = listOf(
            HazeTint(Color.Black.copy(alpha = colorAlpha))
        ),
        backgroundColor = Color.Transparent,
        noiseFactor = .1f
    )
}

@Composable
fun Modifier.enableHazeBorder(shape: Shape): Modifier {
    return this.border(
        width = 1.dp,
        brush = Brush.linearGradient(
            colors = listOf(
                Color.White.copy(alpha = .4f),
                Color.White.copy(alpha = .06f)
            ),
            start = Offset(0f, 0f),
            end = Offset(0f, Float.POSITIVE_INFINITY)
        ),
        shape = shape
    )
}