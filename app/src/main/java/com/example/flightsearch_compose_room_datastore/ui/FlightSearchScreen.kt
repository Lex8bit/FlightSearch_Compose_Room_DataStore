package com.example.flightsearch_compose_room_datastore.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.flightsearch_compose_room_datastore.ui.app_favorite_screen.FavoriteViewModel
import com.example.flightsearch_compose_room_datastore.ui.app_flight_screen.FlightViewModel
import com.example.flightsearch_compose_room_datastore.ui.app_search_screen.SearchTextField
import com.example.flightsearch_compose_room_datastore.ui.app_search_screen.SearchViewModel
import com.example.flightsearch_compose_room_datastore.ui.navigation.FlightSearchNavHost


@Composable
fun FlightSearchApp(navController: NavHostController = rememberNavController()) {
    FlightSearchNavHost(navController = navController)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlightSearchTopAppBar(
    title: String,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(title) },
        modifier = modifier
    )
}


