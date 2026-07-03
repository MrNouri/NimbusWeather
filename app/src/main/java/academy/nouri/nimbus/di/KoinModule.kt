package academy.nouri.nimbus.di

import academy.nouri.nimbus.data.local.data_store.SettingsStoreManager
import academy.nouri.nimbus.data.local.database.CityDatabase
import academy.nouri.nimbus.data.local.database.DatabaseFactory
import academy.nouri.nimbus.data.remote.HttpClientFactory
import academy.nouri.nimbus.data.remote.image.RemoteWallpaperDataSource
import academy.nouri.nimbus.data.remote.image.WallpaperDataSource
import academy.nouri.nimbus.data.remote.weather.RemoteWeatherDataSource
import academy.nouri.nimbus.data.remote.weather.WeatherDataSource
import academy.nouri.nimbus.data.repository.CitiesRepositoryImpl
import academy.nouri.nimbus.data.repository.DashboardRepositoryImpl
import academy.nouri.nimbus.domain.repository.CitiesRepository
import academy.nouri.nimbus.domain.repository.DashboardRepository
import academy.nouri.nimbus.presentation.cities.CitiesViewModel
import academy.nouri.nimbus.presentation.dashboard.DashboardViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val koinModule = module {

    //Http clients
    single(Qualifier.WEATHER_CLIENT) {
        HttpClientFactory.createWeatherClient()
    }
    single(Qualifier.IMAGE_CLIENT) {
        HttpClientFactory.createImageClient()
    }

    //Database
    single { DatabaseFactory.create(get()) }
    single { get<CityDatabase>().cityDao() }

    //Data sources
    single { WeatherDataSource(get(Qualifier.WEATHER_CLIENT)) }.bind<RemoteWeatherDataSource>()
    single { WallpaperDataSource(get(Qualifier.IMAGE_CLIENT)) }.bind<RemoteWallpaperDataSource>()

    //Repositories
    singleOf(::DashboardRepositoryImpl).bind<DashboardRepository>()
    singleOf(::CitiesRepositoryImpl).bind<CitiesRepository>()

    //View models
    viewModelOf(::DashboardViewModel)
    viewModelOf(::CitiesViewModel)

    //Generals
    single { SettingsStoreManager(get()) }
}
