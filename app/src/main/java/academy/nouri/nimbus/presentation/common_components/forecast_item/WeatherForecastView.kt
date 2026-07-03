package academy.nouri.nimbus.presentation.common_components.forecast_item

import academy.nouri.nimbus.domain.model.ForecastData
import academy.nouri.nimbus.presentation.theme.NimbusTheme
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun WeatherForecastView(
    modifier: Modifier = Modifier,
    items: List<ForecastData>
) {
    val density = LocalDensity.current
    val itemWidthDp = 60.dp
    val itemWidthPx = with(density) { itemWidthDp.toPx() }
    val scrollState = rememberLazyListState()

    val scrollOffsetPx by remember {
        derivedStateOf {
            val firstVisible = scrollState.firstVisibleItemIndex
            val firstVisibleOffset = scrollState.firstVisibleItemScrollOffset
            firstVisible * itemWidthPx.toInt() + firstVisibleOffset
        }
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(250.dp)
    ) {
        LazyRow(
            modifier = Modifier.fillMaxSize(),
            state = scrollState
        ) {
            items(items) { item ->
                WeatherForecastItem(item = item)
            }
        }

        ForecastLineChart(
            modifier = Modifier.fillMaxWidth()
                .height(150.dp)
                .align(Alignment.BottomCenter),
            items = items,
            itemWidthPx = itemWidthPx,
            scrollOffsetPx = scrollOffsetPx
        )
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xff000
)
@Composable
fun WeatherForecastViewPreview() {
    NimbusTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.primary),
            contentAlignment = Alignment.Center
        ) {
            WeatherForecastView(items = getMockForecastItems())
        }
    }
}