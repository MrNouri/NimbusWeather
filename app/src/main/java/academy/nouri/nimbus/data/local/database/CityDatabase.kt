package academy.nouri.nimbus.data.local.database

import academy.nouri.nimbus.data.local.database.dao.CityDao
import academy.nouri.nimbus.data.local.database.entity.CityEntity
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [CityEntity::class], version = 4, exportSchema = false)
abstract class CityDatabase : RoomDatabase() {
    abstract fun cityDao(): CityDao

    companion object {
        //Migration from 2 to 3
        val migrate2To3 = object : Migration(2, 3) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL(
                    "ALTER TABLE cities_table ADD COLUMN isSelected INTEGER NOT NULL DEFAULT 0"
                )
            }
        }
    }
}