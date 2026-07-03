package academy.nouri.nimbus.data.local.data_store

import academy.nouri.nimbus.data.utils.Constants.SETTINGS_CITY_LAT
import academy.nouri.nimbus.data.utils.Constants.SETTINGS_CITY_LON
import academy.nouri.nimbus.data.utils.Constants.SETTINGS_MANAGER
import academy.nouri.nimbus.data.utils.Constants.SETTINGS_WEATHER_UNITS
import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(name = SETTINGS_MANAGER)

val WEATHER_UNITS = booleanPreferencesKey(name = SETTINGS_WEATHER_UNITS)
val CITY_LAT = doublePreferencesKey(name = SETTINGS_CITY_LAT)
val CITY_LON = doublePreferencesKey(name = SETTINGS_CITY_LON)

class SettingsStoreManager(private val context: Context) {

    //<editor-fold desc="Metric unit">
    suspend fun saveWeatherUnits(isMetric: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[WEATHER_UNITS] = isMetric
        }
    }

    val isMetric: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[WEATHER_UNITS] ?: true
        }
    //</editor-fold>

    //<editor-fold desc="User location">
    suspend fun saveUserLocation(lat: Double, lon: Double) {
        context.dataStore.edit { preferences ->
            preferences[CITY_LAT] = lat
            preferences[CITY_LON] = lon
        }
    }

    val userLocation: Flow<Pair<Double, Double>> = context.dataStore.data
        .map { preferences ->
            val lat = preferences[CITY_LAT] ?: 0.0
            val lon = preferences[CITY_LON] ?: 0.0
            Pair(lat, lon)
        }
    //</editor-fold>
}