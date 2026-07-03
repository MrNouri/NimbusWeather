package academy.nouri.nimbus.data.mapper

import academy.nouri.nimbus.data.remote.model.weather.WeatherResponse
import academy.nouri.nimbus.domain.model.WeatherData

fun WeatherResponse.toWeatherData(): WeatherData {
    return WeatherData(
        temperature = main?.temp ?: 0.0,
        tempMin = main?.tempMin ?: 0.0,
        tempMax = main?.tempMax ?: 0.0,
        feelsLike = main?.feelsLike ?: 0.0,
        description = weather?.firstOrNull()?.description ?: "",
        icon = weather?.firstOrNull()?.icon ?: "",
        humidity = main?.humidity ?: 0,
        windSpeed = wind?.speed ?: 0.0,
        cityName = name ?: "",
        country = sys?.country ?: "",
        pressure = main?.pressure ?: 0,
        visibility = visibility ?: 0
    )
}
