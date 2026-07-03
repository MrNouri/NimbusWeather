package academy.nouri.nimbus.data.mapper

import academy.nouri.nimbus.data.remote.model.weather.AirPollutionResponse
import academy.nouri.nimbus.domain.model.AirPollutionData

fun AirPollutionResponse.toAirQuality(): AirPollutionData = AirPollutionData(
    quality = list?.firstOrNull()?.main?.aqi ?: 0
)