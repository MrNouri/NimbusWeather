package academy.nouri.nimbus.presentation.dashboard

import academy.nouri.nimbus.domain.model.WeatherData
import academy.nouri.nimbus.domain.repository.DashboardRepository
import academy.nouri.nimbus.domain.utils.DataError
import academy.nouri.nimbus.domain.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ViewModelTest {
    private lateinit var viewmodel: DashboardViewModel
    private lateinit var fakeRepository: FakeDashboardRepository
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        fakeRepository = FakeDashboardRepository()
    }

    @Test
    fun `when weather is fetch successfully, state should be updated with data`() = runTest {
        val mockData = WeatherData(
            temperature = 25.0,
            tempMin = 13.0,
            tempMax = 30.0,
            feelsLike = 27.0,
            description = "Sunny",
            icon = "01d",
            humidity = 50,
            windSpeed = 5.0,
            cityName = "Tehran",
            country = "IR",
            pressure = 1012,
            visibility = 10000
        )
        fakeRepository.result = Result.Success(mockData)
        viewmodel = DashboardViewModel(fakeRepository)

        advanceUntilIdle()

        val currentState = viewmodel.state.value
        assertEquals(false, currentState.isLoading)
        assertEquals(mockData, currentState.weatherData)
        assertEquals(null, currentState.errorMessage)
    }

    class FakeDashboardRepository : DashboardRepository {
        var result: Result<WeatherData, DataError> = Result.Failure(DataError.NoInternet)
        override suspend fun getCurrentWeather(
            lat: Double,
            lng: Double,
            units: String
        ): Result<WeatherData, DataError> {
            return result
        }
    }
}