package academy.nouri.nimbus.data.remote.model.weather

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class CityResponse(
    @SerialName("country")
    val country: String? = null, // IR
    @SerialName("lat")
    val lat: Double? = null, // 38.4193239
    @SerialName("local_names")
    val localNames: LocalNames? = null,
    @SerialName("lon")
    val lon: Double? = null, // 48.8715278
    @SerialName("name")
    val name: String? = null, // Astara
    @SerialName("state")
    val state: String? = null // Gilan Province
) {
    @Serializable
    data class LocalNames(
        @SerialName("ar")
        val ar: String? = null, // آستارا
        @SerialName("bn")
        val bn: String? = null, // আস্তারা
        @SerialName("de")
        val de: String? = null, // Astara
        @SerialName("en")
        val en: String? = null, // Astara
        @SerialName("fa")
        val fa: String? = null, // آستارا
        @SerialName("fr")
        val fr: String? = null, // Astara
        @SerialName("hr")
        val hr: String? = null, // Astara
        @SerialName("ja")
        val ja: String? = null, // アースターラー
        @SerialName("ku")
        val ku: String? = null, // Astara
        @SerialName("ml")
        val ml: String? = null // ആസ്താറ
    )
}