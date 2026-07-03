package academy.nouri.nimbus.presentation.dashboard

interface DashboardActions {
    data class WallpaperBrightnessAnalyze(val isDarkness: Boolean) : DashboardActions
    data class ToggleWeatherUnit(val isMetric: Boolean): DashboardActions
    data object OpenCitiesScreen: DashboardActions
}