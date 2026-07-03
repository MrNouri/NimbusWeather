package academy.nouri.nimbus.presentation.common_components.forecast_item

import academy.nouri.nimbus.domain.model.ForecastData

fun getMockForecastItems(): List<ForecastData> {
    val baseTime = System.currentTimeMillis()
    val threeHours = 3 * 60 * 60 * 1000L

    val icons = listOf("01d", "02d", "03d", "04d", "09d", "10d", "11d", "13d")
    val temps = listOf(
        22f, 21f, 19f, 17f, 15f, 14f, 16f, 18f, 20f, 23f,
        25f, 24f, 22f, 20f, 18f, 16f, 15f, 17f, 19f, 21f,
        23f, 22f, 20f, 18f, 16f, 14f, 13f, 15f, 17f, 20f,
        22f, 21f, 19f, 17f, 16f, 18f, 20f, 22f, 23f, 19f
    )

    return List(40) { index ->
        ForecastData(
            dateTime = baseTime + (index * threeHours),
            temp = temps[index],
            icon = icons[index % icons.size],
            description = "cloudy"
        )
    }
}