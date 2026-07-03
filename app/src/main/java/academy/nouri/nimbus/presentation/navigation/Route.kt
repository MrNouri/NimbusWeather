package academy.nouri.nimbus.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface Route {
    @Serializable
    data object DashboardScreen : Route

    @Serializable
    data object CitiesScreen : Route
}