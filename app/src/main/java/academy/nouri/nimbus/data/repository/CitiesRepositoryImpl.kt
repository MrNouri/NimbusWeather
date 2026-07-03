package academy.nouri.nimbus.data.repository

import academy.nouri.nimbus.data.local.database.dao.CityDao
import academy.nouri.nimbus.data.local.database.entity.CityEntity
import academy.nouri.nimbus.data.mapper.toCityData
import academy.nouri.nimbus.data.mapper.toWallpaperData
import academy.nouri.nimbus.data.remote.image.RemoteWallpaperDataSource
import academy.nouri.nimbus.data.remote.weather.RemoteWeatherDataSource
import academy.nouri.nimbus.domain.model.CityData
import academy.nouri.nimbus.domain.model.WallpaperData
import academy.nouri.nimbus.domain.repository.CitiesRepository
import academy.nouri.nimbus.domain.utils.DataError
import academy.nouri.nimbus.domain.utils.Result
import academy.nouri.nimbus.domain.utils.map
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.Flow

class CitiesRepositoryImpl(
    val weatherDataSource: RemoteWeatherDataSource,
    private val wallpaperDataSource: RemoteWallpaperDataSource,
    private val cityDao: CityDao
) : CitiesRepository {

    override suspend fun getCities(search: String): Result<List<CityData>, DataError> {
        return weatherDataSource.getCities(
            search = search
        ).map { it.toCityData() }
    }

    override suspend fun getWallpaper(query: String): Result<WallpaperData, DataError> {
        return wallpaperDataSource.getWallpaper(
            query = query,
            isPortrait = false
        ).map { it.toWallpaperData() }
    }

    override suspend fun insertCity(entity: CityEntity): Result<Unit, DataError> {
        return try {
            cityDao.insertCity(entity)
            Result.Success(Unit)
        } catch (e: Exception) {
            if (e is CancellationException) throw e
            Result.Failure(error = DataError.Unknown(e.message))
        }
    }

    override suspend fun deleteCity(entity: CityEntity): Result<Unit, DataError> {
        return try {
            cityDao.deleteCity(entity)
            Result.Success(Unit)
        } catch (e: Exception) {
            if (e is CancellationException) throw e
            Result.Failure(error = DataError.Unknown(e.message))
        }
    }

    override suspend fun updateDefaultCity(
        lat: Double, lon: Double
    ): Result<Unit, DataError> {
        return try {
            cityDao.updateDefaultCity(
                lat = lat,
                lon = lon
            )
            Result.Success(Unit)
        } catch (e: Exception) {
            if (e is CancellationException) throw e
            Result.Failure(error = DataError.Unknown(e.message))
        }
    }

    override fun getAllCities(): Flow<List<CityEntity>> {
        return cityDao.getAllCities()
    }
}