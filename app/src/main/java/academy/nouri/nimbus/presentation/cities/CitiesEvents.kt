package academy.nouri.nimbus.presentation.cities

interface CitiesEvents {
    data object SuccessSavedToast : CitiesEvents
    data class SuccessRemoveToast(val cityName: String) : CitiesEvents
}