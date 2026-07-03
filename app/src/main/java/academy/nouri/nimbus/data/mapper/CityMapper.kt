package academy.nouri.nimbus.data.mapper

import academy.nouri.nimbus.data.remote.model.weather.CityResponse
import academy.nouri.nimbus.domain.model.CityData

fun List<CityResponse>.toCityData(): List<CityData> {
    return this.map { item ->
        CityData(
            name = item.name.orEmpty(),
            state = item.state.orEmpty(),
            country = item.country.orEmpty(),
            lat = item.lat ?: 0.0,
            lon = item.lon ?: 0.0
        )
    }
}