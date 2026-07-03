package academy.nouri.nimbus.presentation.common_components

import academy.nouri.nimbus.presentation.utils.extensions.enableHazeBorder
import academy.nouri.nimbus.presentation.utils.extensions.enableHazeEffect
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.materials.ExperimentalHazeMaterialsApi

@OptIn(ExperimentalHazeMaterialsApi::class)
@Composable
fun LiquidGlassCard(
    modifier: Modifier = Modifier,
    hazeState: HazeState,
    roundedCornerSize: Dp = 24.dp,
    isDarkness: Boolean,
    isHardness: Boolean,
    content: @Composable BoxScope.() -> Unit
) {
    val shimmerColors = listOf(
        Color.White.copy(alpha = 0f),
        Color.White.copy(alpha = .15f),
        Color.White.copy(alpha = 0f)
    )

    val colorAlpha = if (isHardness) .4f else .25f
    val blurRadius = if (isHardness) 20.dp else 10.dp

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(roundedCornerSize))
            .hazeEffect(
                state = hazeState,
                //Custom my style
                style = enableHazeEffect(
                    blurRadius = blurRadius,
                    colorAlpha = colorAlpha
                )
                //style = FluentMaterials.thinAcrylic(isDark = isDarkness)
            )
            .enableHazeBorder(
                shape = RoundedCornerShape(roundedCornerSize)
            )
    ) {
        Box(
            modifier = Modifier
                .background(
                    brush = Brush.verticalGradient(shimmerColors)
                )
                .align(Alignment.TopCenter)
        )
        content()
    }
}