package academy.nouri.nimbus.domain.model

data class ForecastData(
    val dateTime: Long,
    val temp: Float,
    val icon: String,
    val description: String
)