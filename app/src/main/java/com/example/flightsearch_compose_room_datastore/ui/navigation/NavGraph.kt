package com.example.flightsearch_compose_room_datastore.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.flightsearch_compose_room_datastore.ui.app_favorite_screen.FavoriteResultScreen
import com.example.flightsearch_compose_room_datastore.ui.app_favorite_screen.FavoriteViewModel
import com.example.flightsearch_compose_room_datastore.ui.app_flight_screen.FlightResultScreen
import com.example.flightsearch_compose_room_datastore.ui.app_search_screen.SearchResultScreen
import com.example.flightsearch_compose_room_datastore.ui.app_search_screen.SearchViewModel


enum class FlightSearchScreen() {
    FavoriteScreen,
    SearchScreen,
    FlightScreen
}
/**
 * Provides Navigation graph for the application.
 */
@Composable
fun FlightSearchNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = FlightSearchScreen.FavoriteScreen.name,
        modifier = modifier
    ) {
        composable(route = FlightSearchScreen.SearchScreen.name) {
            SearchResultScreen(
                navigateToFlightList = { navController.navigate(FlightSearchScreen.FlightScreen.name) },
                navigateToFavorite = { navController.navigate(FlightSearchScreen.FavoriteScreen.name) }
            )
        }
        composable(route = FlightSearchScreen.FavoriteScreen.name) {
            FavoriteResultScreen(
                navigate = { navController.navigate(FlightSearchScreen.SearchScreen.name) },
            )
        }
        composable(route = FlightSearchScreen.FlightScreen.name) {
            FlightResultScreen(
                navigate = { navController.navigate(FlightSearchScreen.SearchScreen.name) },
                navigateToFavorite = { navController.navigate(FlightSearchScreen.FavoriteScreen.name) }
            )
        }
    }
}
