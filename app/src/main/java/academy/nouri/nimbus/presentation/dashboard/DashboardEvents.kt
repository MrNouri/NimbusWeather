package academy.nouri.nimbus.presentation.dashboard

interface DashboardEvents {
    data class AnalyzeWallpaperColor(val imagePath: String) : DashboardEvents
    data object NavigateToCitiesPage: DashboardEvents
}