package academy.nouri.nimbus.data.mapper

import academy.nouri.nimbus.data.remote.model.weather.ForecastResponse
import academy.nouri.nimbus.domain.model.ForecastData

fun ForecastResponse.toForecastData(): List<ForecastData> {
    return list?.mapNotNull { item ->
        item?.let {
            ForecastData(
                dateTime = (it.dt?.toLong() ?: 0) * 1000,
                temp = it.main?.temp?.toFloat() ?: 0f,
                icon = it.weather?.firstOrNull()?.icon ?: "",
                description = it.weather?.firstOrNull()?.description ?: ""
            )
        }
    } ?: emptyList()
}