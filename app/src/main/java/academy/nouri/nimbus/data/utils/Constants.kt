package academy.nouri.nimbus.data.utils

import academy.nouri.nimbus.BuildConfig

object Constants {
    //Weather
    const val API_KEY_WEATHER = BuildConfig.OPEN_WEATHER_API_KEY
    const val HOST_WEATHER = "api.openweathermap.org"
    const val HOST_WEATHER_ICON = "https://openweathermap.org/payload/api/media/file/"

    //Database
    const val TABLE_NAME = "cities_table"
    const val DATABASE_NAME = "cities_database"

    //Wallpaper
    const val API_KEY_WALLPAPER = BuildConfig.PEXELS_API_KEY
    const val HOST_WALLPAPER = "api.pexels.com/v1"

    //Times
    const val HTTP_TIME_OUT = 30_000L

    //API default parameters
    const val METRIC = "metric"
    const val PORTRAIT = "portrait"
    const val IMAGE_COLOR = "black"

    //Keys
    const val AUTHORIZATION = "Authorization"
    const val CLIENT_ID = "Client-ID"

    //DI named
    const val DI_WEATHER_NAMED = "weather_client"
    const val DI_IMAGE_NAMED = "image_client"

    //Data Store
    const val SETTINGS_MANAGER = "settings_manager"
    const val SETTINGS_WEATHER_UNITS = "settings_weather_units"
    const val SETTINGS_CITY_LAT = "settings_city_lat"
    const val SETTINGS_CITY_LON = "settings_city_lon"

    //Other
    const val PNG = ".png"
}
