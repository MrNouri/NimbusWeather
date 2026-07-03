package academy.nouri.nimbus.domain.model

data class WeatherData(
    val temperature: Double,
    val tempMin: Double,
    val tempMax: Double,
    val feelsLike: Double,
    val description: String,
    val icon: String,
    val humidity: Int,
    val windSpeed: Double,
    val cityName: String,
    val country: String,
    val pressure: Int,
    val visibility: Int
)