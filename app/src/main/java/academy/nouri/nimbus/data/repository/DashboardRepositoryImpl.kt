package academy.nouri.nimbus.data.repository

import academy.nouri.nimbus.data.mapper.toAirQuality
import academy.nouri.nimbus.data.mapper.toForecastData
import academy.nouri.nimbus.data.mapper.toWallpaperData
import academy.nouri.nimbus.data.mapper.toWeatherData
import academy.nouri.nimbus.data.remote.image.RemoteWallpaperDataSource
import academy.nouri.nimbus.data.remote.weather.RemoteWeatherDataSource
import academy.nouri.nimbus.domain.model.AirPollutionData
import academy.nouri.nimbus.domain.model.ForecastData
import academy.nouri.nimbus.domain.model.WallpaperData
import academy.nouri.nimbus.domain.model.WeatherData
import academy.nouri.nimbus.domain.repository.DashboardRepository
import academy.nouri.nimbus.domain.utils.DataError
import academy.nouri.nimbus.domain.utils.Result
import academy.nouri.nimbus.domain.utils.map

class DashboardRepositoryImpl(
    private val weatherDataSource: RemoteWeatherDataSource,
    private val wallpaperDataSource: RemoteWallpaperDataSource
) : DashboardRepository {

    override suspend fun getCurrentWeather(
        lat: Double,
        lng: Double,
        units: String
    ): Result<WeatherData, DataError> {
        return weatherDataSource.getCurrentWeather(
            lat = lat,
            lng = lng,
            units = units
        ).map { it.toWeatherData() }
    }

    override suspend fun getWallpaper(query: String): Result<WallpaperData, DataError> {
        return wallpaperDataSource.getWallpaper(
            query = query,
            isPortrait = false
        ).map { it.toWallpaperData() }
    }

    override suspend fun getAirPollution(
        lat: Double,
        lng: Double
    ): Result<AirPollutionData, DataError> {
        return weatherDataSource.getAirPollution(
            lat = lat,
            lng = lng
        ).map { it.toAirQuality() }
    }

    override suspend fun getForecast(
        lat: Double,
        lng: Double,
        units: String
    ): Result<List<ForecastData>, DataError> {
        return weatherDataSource.getForecast(
            lat = lat,
            lng = lng,
            units = units
        ).map { it.toForecastData() }
    }
}