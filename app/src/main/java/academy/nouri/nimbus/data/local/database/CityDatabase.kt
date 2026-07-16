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
        //Migration from 1 to 2
        val migrate1To2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL(
                    "CREATE TABLE IF NOT EXISTS cities_table (city TEXT NOT NULL, county TEXT NOT NULL, lat REAL NOT NULL, lon REAL NOT NULL, PRIMARY KEY(city))"
                )
            }
        }

        //Migration from 2 to 3
        val migrate2To3 = object : Migration(2, 3) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL(
                    "ALTER TABLE cities_table ADD COLUMN isSelected INTEGER NOT NULL DEFAULT 0"
                )
            }
        }

        //Migration from 3 to 4
        val migrate3To4 = object : Migration(3, 4) {
            override fun migrate(db: SupportSQLiteDatabase) {
                // No schema change, version bump only
            }
        }
    }
}