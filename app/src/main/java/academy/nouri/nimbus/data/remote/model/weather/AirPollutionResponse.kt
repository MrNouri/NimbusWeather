package academy.nouri.nimbus.data.remote.model.weather

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AirPollutionResponse(
    @SerialName("coord")
    val coord: Coord? = null,
    @SerialName("list")
    val list: List<Item0?>? = null
) {
    @Serializable
    data class Coord(
        @SerialName("lat")
        val lat: Double? = null, // 35.6893
        @SerialName("lon")
        val lon: Double? = null // 51.3896
    )

    @Serializable
    data class Item0(
        @SerialName("components")
        val components: Components? = null,
        @SerialName("dt")
        val dt: Int? = null, // 1780244358
        @SerialName("main")
        val main: Main? = null
    ) {
        @Serializable
        data class Components(
            @SerialName("co")
            val co: Double? = null, // 83.56
            @SerialName("nh3")
            val nh3: Double? = null, // 0.44
            @SerialName("no")
            val no: Double? = null, // 0.01
            @SerialName("no2")
            val no2: Double? = null, // 2.41
            @SerialName("o3")
            val o3: Double? = null, // 125.47
            @SerialName("pm10")
            val pm10: Double? = null, // 9.43
            @SerialName("pm2_5")
            val pm25: Double? = null, // 3.66
            @SerialName("so2")
            val so2: Double? = null // 1.36
        )

        @Serializable
        data class Main(
            @SerialName("aqi")
            val aqi: Int? = null // 3
        )
    }
}