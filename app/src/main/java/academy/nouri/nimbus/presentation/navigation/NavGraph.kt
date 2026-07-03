package academy.nouri.nimbus.presentation.navigation

import academy.nouri.nimbus.presentation.cities.CitiesScreen
import academy.nouri.nimbus.presentation.cities.CitiesViewModel
import academy.nouri.nimbus.presentation.dashboard.DashboardScreen
import academy.nouri.nimbus.presentation.dashboard.DashboardViewModel
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import org.koin.androidx.compose.koinViewModel

@Composable
fun NavGraph(
    navController: NavHostController,
    paddingValues: PaddingValues
) {
    NavHost(
        navController = navController,
        startDestination = Route.DashboardScreen
    ) {
        composable<Route.DashboardScreen> {
            val viewmodel = koinViewModel<DashboardViewModel>()
            val state by viewmodel.state.collectAsStateWithLifecycle()
            DashboardScreen(
                states = state,
                events = viewmodel.event,
                paddingValues = paddingValues,
                onAction = viewmodel::onAction,
                onNavigateToCitiesPage = {
                    navController.navigate(Route.CitiesScreen)
                }
            )
        }

        composable<Route.CitiesScreen> {
            val viewmodel = koinViewModel<CitiesViewModel>()
            val state by viewmodel.state.collectAsStateWithLifecycle()
            CitiesScreen(
                state = state,
                events = viewmodel.event,
                onCityNameChange = viewmodel::onCityNameChanged,
                onAction = viewmodel::onAction,
                onNavigateBack = {
                    navController.navigateUp()
                }
            )
        }
    }
}