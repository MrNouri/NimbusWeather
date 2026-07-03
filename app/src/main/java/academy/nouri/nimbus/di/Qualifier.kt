package academy.nouri.nimbus.di

import academy.nouri.nimbus.data.utils.Constants.DI_IMAGE_NAMED
import academy.nouri.nimbus.data.utils.Constants.DI_WEATHER_NAMED
import org.koin.core.qualifier.named

object Qualifier {
    val WEATHER_CLIENT = named(DI_WEATHER_NAMED)
    val IMAGE_CLIENT = named(DI_IMAGE_NAMED)
}