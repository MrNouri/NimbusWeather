package academy.nouri.nimbus.domain.repository

import academy.nouri.nimbus.data.local.database.entity.CityEntity
import academy.nouri.nimbus.domain.model.CityData
import academy.nouri.nimbus.domain.model.WallpaperData
import academy.nouri.nimbus.domain.utils.DataError
import academy.nouri.nimbus.domain.utils.Result
import kotlinx.coroutines.flow.Flow

interface CitiesRepository {
    suspend fun getCities(search: String): Result<List<CityData>, DataError>
    suspend fun getWallpaper(query: String): Result<WallpaperData, DataError>
    suspend fun insertCity(entity: CityEntity): Result<Unit, DataError>
    suspend fun deleteCity(entity: CityEntity): Result<Unit, DataError>
    suspend fun updateDefaultCity(lat: Double, lon: Double): Result<Unit, DataError>
    fun getAllCities(): Flow<List<CityEntity>>
}