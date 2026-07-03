package academy.nouri.nimbus.data.remote.weather

import academy.nouri.nimbus.data.remote.model.weather.AirPollutionResponse
import academy.nouri.nimbus.data.remote.model.weather.CityResponse
import academy.nouri.nimbus.data.remote.model.weather.ForecastResponse
import academy.nouri.nimbus.data.remote.model.weather.WeatherResponse
import academy.nouri.nimbus.data.remote.safeCall
import academy.nouri.nimbus.domain.utils.DataError
import academy.nouri.nimbus.domain.utils.Result
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class WeatherDataSource(private val client: HttpClient) : RemoteWeatherDataSource {

    override suspend fun getCurrentWeather(
        lat: Double,
        lng: Double,
        units: String
    ): Result<WeatherResponse, DataError> {
        return safeCall<WeatherResponse> {
            client.get("data/2.5/weather") {
                parameter("lat", lat)
                parameter("lon", lng)
                parameter("units", units)
            }
        }
    }

    override suspend fun getAirPollution(
        lat: Double,
        lng: Double,
    ): Result<AirPollutionResponse, DataError> {
        return safeCall<AirPollutionResponse> {
            client.get("data/2.5/air_pollution") {
                parameter("lat", lat)
                parameter("lon", lng)
            }
        }
    }

    override suspend fun getForecast(
        lat: Double,
        lng: Double,
        units: String
    ): Result<ForecastResponse, DataError> {
        return safeCall<ForecastResponse> {
            client.get("data/2.5/forecast") {
                parameter("lat", lat)
                parameter("lon", lng)
                parameter("units", units)
            }
        }
    }

    override suspend fun getCities(
        search: String
    ): Result<List<CityResponse>, DataError> {
        return safeCall<List<CityResponse>> {
            client.get("geo/1.0/direct") {
                parameter("q", search)
                parameter("limit", 3)
            }
        }
    }
}