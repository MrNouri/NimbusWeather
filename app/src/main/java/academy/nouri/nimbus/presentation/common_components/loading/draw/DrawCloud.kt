package academy.nouri.nimbus.presentation.common_components.loading.draw

import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope

fun DrawScope.drawCloud(
    centerX: Float,
    centerY: Float,
    scale: Float
) {
    val cloudColor = Color(0xFFDFEAF8)
    val cloudColorDark = Color(0xFFB8CDE8)

    // Main cloud body
    val circles = listOf(
        Triple(centerX - 18f * scale, centerY + 4f * scale, 15f * scale), //Left bump
        Triple(centerX + 0f * scale, centerY - 2f * scale, 24f * scale), //Center bump
        Triple(centerX + 21.5f * scale, centerY + 5f * scale, 17f * scale) //Right bump
    )

    //Bottom fill rect
    drawRoundRect(
        color = cloudColor,
        topLeft = Offset(
            x = centerX - 38f * scale,
            y = centerY + 2f * scale
        ),
        size = Size(76f * scale, 20f * scale),
        cornerRadius = CornerRadius(10f * scale)
    )

    circles.forEach { (x, y, r) ->
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(Color.White, cloudColor),
                center = Offset(x, y),
                radius = r
            ),
            radius = r,
            center = Offset(x, y)
        )
    }

    //Bottom shadow line
    drawRoundRect(
        color = cloudColorDark.copy(alpha = .4f),
        topLeft = Offset(
            x = centerX - 36f * scale,
            y = centerY + 18f * scale
        ),
        size = Size(70f * scale, 3f * scale),
        cornerRadius = CornerRadius(4f * scale)
    )
}