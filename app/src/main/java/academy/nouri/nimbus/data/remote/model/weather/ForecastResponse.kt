package academy.nouri.nimbus.data.remote.model.weather

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ForecastResponse(
    @SerialName("city")
    val city: City? = null,
    @SerialName("cnt")
    val cnt: Int? = null, // 40
    @SerialName("cod")
    val cod: String? = null, // 200
    @SerialName("list")
    val list: List<Item0?>? = null,
    @SerialName("message")
    val message: Int? = null // 0
) {
    @Serializable
    data class City(
        @SerialName("coord")
        val coord: Coord? = null,
        @SerialName("country")
        val country: String? = null, // IR
        @SerialName("id")
        val id: Int? = null, // 112931
        @SerialName("name")
        val name: String? = null, // Tehran
        @SerialName("population")
        val population: Int? = null, // 7153309
        @SerialName("sunrise")
        val sunrise: Int? = null, // 1780363193
        @SerialName("sunset")
        val sunset: Int? = null, // 1780415113
        @SerialName("timezone")
        val timezone: Int? = null // 12600
    ) {
        @Serializable
        data class Coord(
            @SerialName("lat")
            val lat: Double? = null, // 35.6893
            @SerialName("lon")
            val lon: Double? = null // 51.3896
        )
    }

    @Serializable
    data class Item0(
        @SerialName("clouds")
        val clouds: Clouds? = null,
        @SerialName("dt")
        val dt: Int? = null, // 1780423200
        @SerialName("dt_txt")
        val dtTxt: String? = null, // 2026-06-02 18:00:00
        @SerialName("main")
        val main: Main? = null,
        @SerialName("pop")
        val pop: Double? = null, // 0.2
        @SerialName("rain")
        val rain: Rain? = null,
        @SerialName("sys")
        val sys: Sys? = null,
        @SerialName("visibility")
        val visibility: Int? = null, // 10000
        @SerialName("weather")
        val weather: List<Weather?>? = null,
        @SerialName("wind")
        val wind: Wind? = null
    ) {
        @Serializable
        data class Clouds(
            @SerialName("all")
            val all: Int? = null // 13
        )

        @Serializable
        data class Main(
            @SerialName("feels_like")
            val feelsLike: Double? = null, // 300.72
            @SerialName("grnd_level")
            val grndLevel: Int? = null, // 864
            @SerialName("humidity")
            val humidity: Int? = null, // 10
            @SerialName("pressure")
            val pressure: Int? = null, // 1011
            @SerialName("sea_level")
            val seaLevel: Int? = null, // 1011
            @SerialName("temp")
            val temp: Double? = null, // 302.41
            @SerialName("temp_kf")
            val tempKf: Double? = null, // 1.39
            @SerialName("temp_max")
            val tempMax: Double? = null, // 302.41
            @SerialName("temp_min")
            val tempMin: Double? = null // 301.02
        )

        @Serializable
        data class Rain(
            @SerialName("3h")
            val h: Double? = null // 0.15
        )

        @Serializable
        data class Sys(
            @SerialName("pod")
            val pod: String? = null // n
        )

        @Serializable
        data class Weather(
            @SerialName("description")
            val description: String? = null, // few clouds
            @SerialName("icon")
            val icon: String? = null, // 02n
            @SerialName("id")
            val id: Int? = null, // 801
            @SerialName("main")
            val main: String? = null // Clouds
        )

        @Serializable
        data class Wind(
            @SerialName("deg")
            val deg: Int? = null, // 6
            @SerialName("gust")
            val gust: Double? = null, // 5.26
            @SerialName("speed")
            val speed: Double? = null // 3.18
        )
    }
}