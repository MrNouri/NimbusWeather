package academy.nouri.nimbus.presentation.common_components.forecast_item

import academy.nouri.nimbus.data.utils.Constants.HOST_WEATHER_ICON
import academy.nouri.nimbus.data.utils.Constants.PNG
import academy.nouri.nimbus.domain.model.ForecastData
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun WeatherForecastItem(item: ForecastData) {
    val date = remember(item.dateTime) { Date(item.dateTime) }
    val dayFormat = remember { SimpleDateFormat("EEE", Locale.ENGLISH) }
    val hourFormat = remember { SimpleDateFormat("HH:mm", Locale.ENGLISH) }

    Column(
        modifier = Modifier
            .width(65.dp)
            .fillMaxHeight()
            .padding(horizontal = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(8.dp))
        Text(
            text = dayFormat.format(date),
            style = MaterialTheme.typography.labelMedium,
            color = Color.White
        )
        Text(
            text = hourFormat.format(date),
            style = MaterialTheme.typography.labelSmall,
            color = Color.White.copy(alpha = .7f)
        )
        Spacer(Modifier.height(8.dp))
        AsyncImage(
            modifier = Modifier.size(40.dp),
            model = "$HOST_WEATHER_ICON${item.icon}$PNG",
            contentDescription = "WeatherIcon"
        )
        Text(
            text = "${item.temp.toInt()}°",
            style = MaterialTheme.typography.labelSmall,
            color = Color.White,
            fontWeight = FontWeight.SemiBold
        )
    }
}

