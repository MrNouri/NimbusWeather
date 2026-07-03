package academy.nouri.nimbus.data.remote.weather

import academy.nouri.nimbus.data.remote.model.weather.AirPollutionResponse
import academy.nouri.nimbus.data.remote.model.weather.CityResponse
import academy.nouri.nimbus.data.remote.model.weather.ForecastResponse
import academy.nouri.nimbus.data.remote.model.weather.WeatherResponse
import academy.nouri.nimbus.data.utils.Constants
import academy.nouri.nimbus.domain.utils.DataError
import academy.nouri.nimbus.domain.utils.Result

interface RemoteWeatherDataSource {
    suspend fun getCurrentWeather(
        lat: Double,
        lng: Double,
        units: String = Constants.METRIC
    ): Result<WeatherResponse, DataError>

    suspend fun getAirPollution(
        lat: Double,
        lng: Double
    ): Result<AirPollutionResponse, DataError>

    suspend fun getForecast(
        lat: Double,
        lng: Double,
        units: String = Constants.METRIC
    ): Result<ForecastResponse, DataError>

    suspend fun getCities(
        search: String
    ): Result<List<CityResponse>, DataError>
}