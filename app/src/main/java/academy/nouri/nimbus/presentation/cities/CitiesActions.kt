package academy.nouri.nimbus.presentation.cities

interface CitiesActions {
    data class SaveCity(val name: String, val country: String, val lat: Double, val lon: Double) : CitiesActions
    data class RemoveCity(val name: String, val country: String, val lat: Double, val lon: Double) : CitiesActions
    data class SelectDefaultCity(val lat: Double, val lon: Double) : CitiesActions
}