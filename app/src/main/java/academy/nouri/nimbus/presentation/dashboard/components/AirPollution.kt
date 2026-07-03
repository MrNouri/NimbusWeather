package academy.nouri.nimbus.presentation.dashboard.components

import academy.nouri.nimbus.presentation.theme.NimbusTheme
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PaintingStyle
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun AirPollutionView(
    modifier: Modifier = Modifier,
    triangleColor: Color = Color.White,
    airQuality : Int = 1
) {
    val triangleSize = 20f
    val barHeight = 30f
    val clampedAirQuality = airQuality.coerceIn(1,5)

    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(40.dp)
    ) {
        val selectionWight = size.width / 5f
        val triangleCenterX = (clampedAirQuality - 1) * selectionWight + selectionWight / 2f
        val triangleStartX = triangleCenterX - triangleSize / 2f
        val triangleStartY = barHeight + 30f

        drawRoundRect(
            brush = Brush.horizontalGradient(
                colors = listOf(
                    Color(0xFF4CAF82).copy(alpha = 0.8f),
                    Color(0xFF8BC34A).copy(alpha = 0.8f),
                    Color(0xFFCDC052).copy(alpha = 0.8f),
                    Color(0xFFD4845A).copy(alpha = 0.8f),
                    Color(0xFFC0544A).copy(alpha = 0.8f),
                )
            ),
            topLeft = Offset(0f, 0f),
            size = Size(size.width, barHeight),
            cornerRadius = CornerRadius(50f, 50f)
        )

        val trianglePath = Path().apply {
            moveTo(x = triangleStartX + triangleSize / 2f, y = triangleStartY)
            lineTo(x = triangleStartX, y = triangleStartY + triangleSize)
            lineTo(x = triangleStartX + triangleSize, y = triangleStartY + triangleSize)
            close()
        }
        val trianglePaint = Paint().apply {
            color = triangleColor.copy(alpha = 0.9f)
            isAntiAlias = true
            style = PaintingStyle.Stroke
            strokeJoin = StrokeJoin.Round
            strokeWidth = 20f
        }
        drawContext.canvas.drawPath(trianglePath, trianglePaint)
    }
}

@Preview
@Composable
private fun AirPollutionViewPreview() {
    NimbusTheme {
        AirPollutionView()
    }
}