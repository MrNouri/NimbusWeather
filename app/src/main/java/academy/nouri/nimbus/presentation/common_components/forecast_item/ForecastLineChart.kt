package academy.nouri.nimbus.presentation.common_components.forecast_item

import academy.nouri.nimbus.domain.model.ForecastData
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

@Composable
fun ForecastLineChart(
    modifier: Modifier = Modifier,
    items: List<ForecastData>,
    itemWidthPx: Float,
    scrollOffsetPx: Int
) {
    if (items.isEmpty()) return

    Canvas(modifier = modifier.fillMaxSize()) {
        val width = size.width
        val height = size.height

        val minTemp = items.minOfOrNull { it.temp } ?: 0f
        val maxTemp = items.maxOfOrNull { it.temp } ?: 0f
        val tempRange = (maxTemp - minTemp).coerceAtLeast(1f)

        fun tempToY(temp: Float): Float {
            val ratio = (temp - minTemp) / tempRange
            return height * (1f - ratio) * .6f + height * .1f
        }

        fun indexToX(index: Int): Float {
            return index * itemWidthPx - scrollOffsetPx
        }

        val linePath = Path()
        val fillPath = Path()

        items.forEachIndexed { index, item ->
            val x = indexToX(index)
            val y = tempToY(item.temp)

            if (index == 0) {
                linePath.moveTo(0f, y)
                fillPath.moveTo(0f, height)
                fillPath.lineTo(0f, y)
            } else {
                val prevX = indexToX(index - 1)
                val prevY = tempToY(items[index - 1].temp)
                val controlX1 = prevX + (x - prevX) / 3f
                val controlX2 = prevX + (x - prevX) / 3f

                linePath.cubicTo(controlX1, prevY, controlX2, y, x, y)
                fillPath.cubicTo(controlX1, prevY, controlX2, y, x, y)
            }
        }

        val lastY = tempToY(items.last().temp)

        linePath.lineTo(width, lastY)

        fillPath.lineTo(width, lastY)
        fillPath.lineTo(width, height)
        fillPath.close()

        drawPath(
            path = fillPath,
            brush = Brush.verticalGradient(
                colors = listOf(
                    Color.White.copy(alpha = 0.4f),
                    Color.Transparent
                ),
                startY = 0f,
                endY = height
            )
        )

        drawPath(
            path = linePath,
            color = Color.White,
            style = Stroke(
                width = 2.dp.toPx(),
                cap = StrokeCap.Round
            )
        )
    }
}