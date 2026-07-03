package academy.nouri.nimbus.presentation.cities

import academy.nouri.nimbus.data.local.database.entity.CityEntity
import academy.nouri.nimbus.domain.model.CityData

data class CitiesState(
    val loading: Boolean = false,
    val cityName: String = "",
    val citiesList: List<CityData> = emptyList(),
    val storedCitiesList: List<CityEntity> = emptyList(),
    val cityWallpapers: Map<String, String> = emptyMap(),
    val isEmpty: Boolean = true,
    val error: String = ""
)
