package academy.nouri.nimbus.presentation.dashboard

import academy.nouri.nimbus.domain.model.ForecastData
import academy.nouri.nimbus.domain.model.WeatherData

data class DashboardStates(
    val isLoading: Boolean = false,
    val weatherData: WeatherData? = null,
    val errorMessage: String? = null,
    val wallpaperPath: String? = null,
    val isDarkWallpaper: Boolean = false,
    val currentDateString: String? = null,
    val airPollution: Int? = null,
    val forecastList: List<ForecastData>? = null,
    val isMetric: Boolean? = null,
    val hasCity: Boolean? = false
)
