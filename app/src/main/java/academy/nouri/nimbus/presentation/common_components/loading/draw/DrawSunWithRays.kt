package academy.nouri.nimbus.presentation.common_components.loading.draw

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.rotate
import kotlin.math.cos
import kotlin.math.sin

fun DrawScope.drawSunWithRays(
    center: Offset,
    sunX: Float,
    sunY: Float,
    radius: Float,
    rotation: Float,
    alpha: Float
) {
    val rayCount = 10
    val rayStartGap = radius + 6f
    val raySize = radius + 16f
    val rayColor = Color(0xFFFFC947)

    rotate(degrees = rotation, pivot = center) {
        for (i in 0 until rayCount) {
            val angel = Math.toRadians((i * 360.0 / rayCount)).toFloat()
            val start = Offset(
                x = center.x + cos(angel) * rayStartGap,
                y = center.y + sin(angel) * rayStartGap
            )
            val end = Offset(
                x = center.x + cos(angel) * raySize,
                y = center.y + sin(angel) * raySize
            )
            drawLine(
                color = rayColor.copy(alpha = alpha * .75f),
                start = start,
                end = end,
                strokeWidth = 2.5f,
                cap = StrokeCap.Round
            )
        }
    }

    drawCircle(
        brush = Brush.radialGradient(
            colors = listOf(Color(0xFFFFE57A), Color(0xFFFFC947)),
            center = Offset(x = sunX, y = sunY),
            radius = radius
        ),
        radius = radius,
        center = Offset(x = sunX, y = sunY)
    )
}