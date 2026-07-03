package academy.nouri.nimbus.data.local.database

import academy.nouri.nimbus.data.local.database.CityDatabase.Companion.migrate2To3
import academy.nouri.nimbus.data.utils.Constants.DATABASE_NAME
import android.content.Context
import androidx.room.Room

object DatabaseFactory {
    fun create(context: Context): CityDatabase {
        return Room.databaseBuilder(
            context = context.applicationContext,
            klass = CityDatabase::class.java,
            name = DATABASE_NAME
        )
            .fallbackToDestructiveMigration(false)
            .addMigrations(migrate2To3)
            .build()
    }
}