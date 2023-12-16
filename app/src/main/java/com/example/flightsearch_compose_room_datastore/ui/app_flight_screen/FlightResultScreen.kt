package com.example.flightsearch_compose_room_datastore.ui.app_flight_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.flightsearch_compose_room_datastore.ui.FlightSearchTopAppBar
import com.example.flightsearch_compose_room_datastore.ui.app_search_screen.SearchTextField
import com.example.flightsearch_compose_room_datastore.ui.theme.FlightSearch_Compose_Room_DataStoreTheme



@Composable
fun FlightResultScreen(
    navigate : () -> Unit,
    navigateToFavorite: () -> Unit,
    modifier: Modifier = Modifier,
    flightViewModel: FlightViewModel = viewModel(factory = FlightViewModel.Factory)
) {

    val flightUIState by flightViewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            FlightSearchTopAppBar(
                title = "Flight Search",
                modifier = Modifier
            )
        }
    ) { innerPadding ->
        Column(
            modifier = modifier.padding(horizontal = 16.dp)
        ) {
            SearchTextField(
                onEraseItemClick = {
                    flightViewModel.saveSearchInPref(it)
                    navigateToFavorite()
                },
                searchFieldValue = flightUIState.searchField,
                onSearchFieldValueChange = {
                    flightViewModel.saveSearchInPref(it)
                    if (flightUIState.searchField == "")
                        navigateToFavorite()
                    else
                        navigate()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(innerPadding)
            )
            FlightList(
                tableName = flightUIState.tableName,
                airportCardList = flightUIState.airportCards,
                onAirportCardItemStarClick = { flightViewModel.saveOrDeleteInFavorites(it) },
                modifier = Modifier
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FlightResultScreenPreview() {
    FlightSearch_Compose_Room_DataStoreTheme {
        FlightResultScreen(
            navigate ={},
            navigateToFavorite = {}
        )
    }
}