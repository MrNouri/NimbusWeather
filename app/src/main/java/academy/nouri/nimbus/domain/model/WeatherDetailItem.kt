package academy.nouri.nimbus.domain.model

import androidx.compose.ui.graphics.vector.ImageVector

data class WeatherDetailItem(
    val label: String,
    val value: String,
    val icon: ImageVector,
    val unit: String = ""
)
