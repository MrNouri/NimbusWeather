package academy.nouri.nimbus.data.remote

import academy.nouri.nimbus.BuildConfig
import academy.nouri.nimbus.data.utils.Constants.API_KEY_WALLPAPER
import academy.nouri.nimbus.data.utils.Constants.API_KEY_WEATHER
import academy.nouri.nimbus.data.utils.Constants.AUTHORIZATION
import academy.nouri.nimbus.data.utils.Constants.CLIENT_ID
import academy.nouri.nimbus.data.utils.Constants.HOST_WALLPAPER
import academy.nouri.nimbus.data.utils.Constants.HOST_WEATHER
import academy.nouri.nimbus.data.utils.Constants.HTTP_TIME_OUT
import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.ANDROID
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.URLProtocol
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object HttpClientFactory {

    private fun HttpClientConfig<*>.installCommonPlugins() {
        install(ContentNegotiation) {
            json(json = Json {
                ignoreUnknownKeys = true
                coerceInputValues = true
            })
        }
        install(HttpTimeout) {
            socketTimeoutMillis = HTTP_TIME_OUT
            requestTimeoutMillis = HTTP_TIME_OUT
            connectTimeoutMillis = HTTP_TIME_OUT
        }
        if (BuildConfig.DEBUG) {
            install(Logging) {
                level = LogLevel.BODY
                logger = Logger.ANDROID
            }
        }
    }

    fun createWeatherClient(): HttpClient = HttpClient(OkHttp) {
        installCommonPlugins()
        defaultRequest {
            url {
                protocol = URLProtocol.HTTPS
                host = HOST_WEATHER
                parameters.append("appid", API_KEY_WEATHER)
            }
            contentType(ContentType.Application.Json)
        }
    }

    fun createImageClient(): HttpClient = HttpClient(OkHttp) {
        installCommonPlugins()
        defaultRequest {
            url {
                protocol = URLProtocol.HTTPS
                host = HOST_WALLPAPER
            }
            header(
                key = AUTHORIZATION,
                value = API_KEY_WALLPAPER
            )
        }
    }
}
