package academy.nouri.nimbus.data.local.database.entity

import academy.nouri.nimbus.data.utils.Constants.TABLE_NAME
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = TABLE_NAME)
data class CityEntity(
    @PrimaryKey
    val city: String,
    val county: String,
    val isSelected: Boolean = false,
    val lat: Double,
    val lon: Double
)
