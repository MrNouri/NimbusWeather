package academy.nouri.nimbus.domain.repository

import academy.nouri.nimbus.data.utils.Constants.METRIC
import academy.nouri.nimbus.domain.model.AirPollutionData
import academy.nouri.nimbus.domain.model.ForecastData
import academy.nouri.nimbus.domain.model.WallpaperData
import academy.nouri.nimbus.domain.model.WeatherData
import academy.nouri.nimbus.domain.utils.DataError
import academy.nouri.nimbus.domain.utils.Result

interface DashboardRepository {

    suspend fun getCurrentWeather(
        lat: Double,
        lng: Double,
        units: String = METRIC
    ): Result<WeatherData, DataError>

    suspend fun getWallpaper(
        query: String
    ): Result<WallpaperData, DataError>

    suspend fun getAirPollution(
        lat: Double,
        lng: Double
    ): Result<AirPollutionData, DataError>

    suspend fun getForecast(
        lat: Double,
        lng: Double,
        units: String = METRIC
    ): Result<List<ForecastData>, DataError>
}