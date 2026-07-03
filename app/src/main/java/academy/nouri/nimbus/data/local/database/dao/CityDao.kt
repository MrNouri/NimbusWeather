package academy.nouri.nimbus.data.local.database.dao

import academy.nouri.nimbus.data.local.database.entity.CityEntity
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface CityDao {
    @Insert(onConflict = REPLACE)
    suspend fun insertCity(city: CityEntity)

    @Delete
    suspend fun deleteCity(city: CityEntity)

    @Query("SELECT * FROM cities_table")
    fun getAllCities(): Flow<List<CityEntity>>

    @Query("UPDATE cities_table SET isSelected = 0")
    suspend fun resetAllSelection()

    @Query("UPDATE cities_table SET isSelected = 1 WHERE lat=:lat AND lon =:lon")
    suspend fun setSelectedCity(lat: Double, lon: Double)

    @Transaction
    suspend fun updateDefaultCity(lat: Double, lon: Double) {
        resetAllSelection()
        setSelectedCity(lat = lat, lon = lon)
    }
}