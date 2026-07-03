package academy.nouri.nimbus.presentation.common_components.loading.draw

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope

fun DrawScope.drawRainDrop(
    centerX: Float,
    centerY: Float,
    drop1: Float,
    drop2: Float,
    drop3: Float,
    drop4: Float,
) {
    val drops = listOf(
        Triple(centerX - 22f, drop1, 1),
        Triple(centerX - 6f, drop2, 2),
        Triple(centerX + 10f, drop3, 3),
        Triple(centerX + 26f, drop4, 4)
    )

    drops.forEach { (x, progress, _) ->
        val dropY = centerY + 34f + progress * 52f
        val alpha = when {
            progress < .15f -> progress / .15f
            progress > .8f -> 1f - (progress - .8f) / .2f
            else -> 1f
        }

        drawLine(
            color = Color(0xFF7EB8E8).copy(alpha = alpha * 0.85f),
            start = Offset(x, dropY),
            end = Offset(
                x = x - 3f,
                y = dropY + 10f
            ),
            strokeWidth = 2f,
            cap = StrokeCap.Round
        )
    }
}