package academy.nouri.nimbus.presentation.dashboard

import academy.nouri.nimbus.data.local.data_store.SettingsStoreManager
import academy.nouri.nimbus.domain.repository.DashboardRepository
import academy.nouri.nimbus.domain.utils.onFailure
import academy.nouri.nimbus.domain.utils.onSuccess
import academy.nouri.nimbus.presentation.utils.errorMessage
import academy.nouri.nimbus.presentation.utils.extensions.dateFormatter
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class DashboardViewModel(
    private val repository: DashboardRepository,
    private val settingsManager: SettingsStoreManager
) : ViewModel() {

    private val _state = MutableStateFlow(DashboardStates())
    val state = _state.asStateFlow()

    private val _event = Channel<DashboardEvents>()
    val event = _event.receiveAsFlow()

    init {
        observedSettings()
        calculateCurrentDate()
    }

    private fun setupData(lat: Double, lon: Double) = viewModelScope.launch {
        _state.update { it.copy(isLoading = true) }
        val unit = if (state.value.isMetric == true) "metric" else "imperial"

        getCurrentWeather(unit = unit, lat = lat, lon = lon)
        getAirPollution(lat = lat, lon = lon)
        getForecast(unit = unit, lat = lat, lon = lon)

        _state.update { it.copy(isLoading = false) }
    }

    private suspend fun getCurrentWeather(
        unit: String, lat: Double, lon: Double
    ) {
        repository.getCurrentWeather(
            lat = lat,
            lng = lon,
            units = unit
        )
            .onSuccess { data ->
                _state.update {
                    it.copy(weatherData = data)
                }
                val query = data.cityName
                getWallpaperPath(query)
            }
            .onFailure { error ->
                _state.update {
                    it.copy(
                        errorMessage = error.errorMessage()
                    )
                }
            }
    }

    private suspend fun getWallpaperPath(query: String) {
        repository.getWallpaper(query)
            .onSuccess { data ->
                data.wallpaperPath?.let { path ->
                    _state.update { it.copy(wallpaperPath = path) }
                    analyzeBrightnessWallpaper(path)
                }
            }
            .onFailure { error ->
                _state.update {
                    it.copy(
                        errorMessage = error.errorMessage()
                    )
                }
            }
    }

    private suspend fun getAirPollution(lat: Double, lon: Double) {
        repository.getAirPollution(
            lat = lat,
            lng = lon
        )
            .onSuccess { data ->
                _state.update {
                    it.copy(airPollution = data.quality)
                }
            }
            .onFailure { error ->
                _state.update {
                    it.copy(
                        errorMessage = error.errorMessage()
                    )
                }
            }
    }

    private suspend fun getForecast(unit: String, lat: Double, lon: Double) {
        repository.getForecast(
            lat = lat,
            lng = lon,
            units = unit
        )
            .onSuccess { data ->
                _state.update {
                    it.copy(forecastList = data)
                }
            }
            .onFailure { error ->
                _state.update {
                    it.copy(errorMessage = error.errorMessage())
                }
            }
    }

    private suspend fun analyzeBrightnessWallpaper(wallpaperPath: String) {
        _event.send(DashboardEvents.AnalyzeWallpaperColor(wallpaperPath))
    }

    private fun calculateCurrentDate() {
        val currentDate = LocalDateTime.now()
        val formatter = currentDate.format(dateFormatter)
        _state.update { it.copy(currentDateString = formatter) }
    }

    private fun observedSettings() = viewModelScope.launch {
        combine(
            settingsManager.isMetric,
            settingsManager.userLocation
        ) { isMetric, location ->
            isMetric to location
        }.collect { (isMetric, location) ->
            _state.update {
                it.copy(isMetric = isMetric)
            }
            _state.update {
                it.copy(hasCity = location.first > 0)
            }
            setupData(lat = location.first, lon = location.second)
        }
    }

    fun onAction(action: DashboardActions) {
        when (action) {
            is DashboardActions.WallpaperBrightnessAnalyze -> {
                _state.update {
                    it.copy(isDarkWallpaper = action.isDarkness)
                }
            }

            is DashboardActions.ToggleWeatherUnit -> {
                viewModelScope.launch {
                    settingsManager.saveWeatherUnits(action.isMetric)
                }
            }

            is DashboardActions.OpenCitiesScreen -> {
                _event.trySend(DashboardEvents.NavigateToCitiesPage)
            }
        }
    }
}