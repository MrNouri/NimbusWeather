package academy.nouri.nimbus.presentation.cities

import academy.nouri.nimbus.data.local.data_store.SettingsStoreManager
import academy.nouri.nimbus.data.local.database.entity.CityEntity
import academy.nouri.nimbus.domain.repository.CitiesRepository
import academy.nouri.nimbus.domain.utils.onFailure
import academy.nouri.nimbus.domain.utils.onSuccess
import academy.nouri.nimbus.presentation.utils.errorMessage
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

class CitiesViewModel(
    val repository: CitiesRepository,
    val settingsManage: SettingsStoreManager
) : ViewModel() {

    private val _state = MutableStateFlow(CitiesState())
    val state = _state.asStateFlow()

    private val _event = Channel<CitiesEvents>()
    val event = _event.receiveAsFlow()

    init {
        observerCityName()
        getStoredCitiesList()
    }

    @OptIn(FlowPreview::class)
    private fun observerCityName() {
        _state
            .map { it.cityName }
            .distinctUntilChanged()
            .debounce(500.milliseconds)
            .onEach { query ->
                if (query.isNotBlank()) {
                    _state.update {
                        it.copy(loading = true)
                    }
                    searchCityName(query)
                } else {
                    _state.update {
                        it.copy(
                            isEmpty = true,
                            loading = false
                        )
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    private suspend fun searchCityName(query: String) {
        repository.getCities(search = query)
            .onSuccess { citiesList ->
                _state.update {
                    it.copy(
                        citiesList = citiesList,
                        isEmpty = false,
                        loading = false
                    )
                }
            }
            .onFailure { error ->
                _state.update {
                    it.copy(
                        error = error.errorMessage(),
                        loading = false
                    )
                }
            }
    }

    fun onCityNameChanged(query: String) {
        _state.update {
            it.copy(cityName = query)
        }
    }

    private fun getStoredCitiesList() {
        repository.getAllCities()
            .onEach { list ->
                _state.update {
                    it.copy(storedCitiesList = list)
                }
                list.forEach { city ->
                    if (_state.value.cityWallpapers.contains(city.city).not()) {
                        getWallpaperPath(city.city)
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    private suspend fun getWallpaperPath(query: String) {
        repository.getWallpaper(query)
            .onSuccess { data ->
                data.wallpaperPath?.let { path ->
                    _state.update {
                        it.copy(
                            cityWallpapers = it.cityWallpapers + (query to path)
                        )
                    }
                }
            }
            .onFailure { error ->
                _state.update {
                    it.copy(error = error.errorMessage())
                }
            }
    }

    fun onAction(action: CitiesActions) {
        when (action) {
            is CitiesActions.SaveCity -> {
                val cityEntity = CityEntity(
                    city = action.name,
                    county = action.country,
                    lat = action.lat,
                    lon = action.lon
                )
                viewModelScope.launch {
                    repository.insertCity(cityEntity)
                        .onSuccess {
                            _event.send(CitiesEvents.SuccessSavedToast)
                        }
                        .onFailure { error ->
                            _state.update {
                                it.copy(
                                    error = error.errorMessage()
                                )
                            }
                        }
                }
            }

            is CitiesActions.RemoveCity -> {
                val cityEntity = CityEntity(
                    city = action.name,
                    county = action.country,
                    lat = action.lat,
                    lon = action.lon
                )
                viewModelScope.launch {
                    repository.deleteCity(cityEntity)
                        .onSuccess {
                            _event.send(CitiesEvents.SuccessRemoveToast(cityName = action.name))
                        }
                        .onFailure { error ->
                            _state.update {
                                it.copy(
                                    error = error.errorMessage()
                                )
                            }
                        }
                }
            }

            is CitiesActions.SelectDefaultCity -> {
                viewModelScope.launch {
                    repository.updateDefaultCity(lat = action.lat, lon = action.lon)
                        .onSuccess {
                            settingsManage.saveUserLocation(
                                lat = action.lat,
                                lon = action.lon
                            )
                        }
                        .onFailure { error ->
                            _state.update {
                                it.copy(
                                    error = error.errorMessage()
                                )
                            }
                        }
                }
            }
        }
    }
}