package com.example.flightsearch_compose_room_datastore.ui.app_search_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.flightsearch_compose_room_datastore.ui.FlightSearchTopAppBar
import com.example.flightsearch_compose_room_datastore.ui.theme.FlightSearch_Compose_Room_DataStoreTheme



@Composable

fun SearchResultScreen(
    navigateToFlightList: () -> Unit,
    navigateToFavorite: () -> Unit,
    modifier: Modifier = Modifier,
    searchViewModel: SearchViewModel = viewModel(factory = SearchViewModel.Factory)
) {

    val searchUIState = searchViewModel.uiState.collectAsState()

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
                searchFieldValue = searchUIState.value.searchField,
                onSearchFieldValueChange = {
                    searchViewModel.saveSearchInPref(it)
                    if(searchUIState.value.searchField == "")navigateToFavorite()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(innerPadding)

            )
            Spacer(modifier = Modifier.height(16.dp))
            SearchList(
                onAirportItemClick = {
                    searchViewModel.saveSearchInPref(it.iataCode)
                    navigateToFlightList()
                },
                airportItemList = searchUIState.value.airportItemList,
                modifier = Modifier
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SearchResultScreenPreview() {
    FlightSearch_Compose_Room_DataStoreTheme {
        SearchResultScreen(
            navigateToFlightList = {},
            navigateToFavorite = {}
        )
    }
}