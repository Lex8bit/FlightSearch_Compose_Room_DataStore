package com.example.flightsearch_compose_room_datastore.ui.app_favorite_screen

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
import com.example.flightsearch_compose_room_datastore.ui.app_flight_screen.FlightList
import com.example.flightsearch_compose_room_datastore.ui.app_search_screen.SearchTextField
import com.example.flightsearch_compose_room_datastore.ui.theme.FlightSearch_Compose_Room_DataStoreTheme

@Composable
fun FavoriteResultScreen(
    navigate : () -> Unit,
    modifier : Modifier = Modifier,
    favoriteViewModel: FavoriteViewModel = viewModel(factory = FavoriteViewModel.Factory)
){

    val favoriteUIState by favoriteViewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            FlightSearchTopAppBar(
                title = "Flight Search",
                modifier = Modifier
            )
        }
    ) { innerPadding ->
        Column (
            modifier = modifier.padding(horizontal = 16.dp)
        ){
            SearchTextField(
                searchFieldValue = favoriteUIState.searchField,
                onSearchFieldValueChange = {
                    favoriteViewModel.saveSearchInPref(it)
                    navigate()
                },
                modifier = Modifier.fillMaxWidth().padding(innerPadding)
            )
            FlightList(
                tableName = favoriteUIState.tableName,
                airportCardList = favoriteUIState.airportCards,
                onAirportCardItemStarClick = { favoriteViewModel.onStarClick(it) },
                modifier = Modifier
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FlightResultScreenPreview() {
    FlightSearch_Compose_Room_DataStoreTheme {
        FavoriteResultScreen(
            navigate = {},
        )
    }
}