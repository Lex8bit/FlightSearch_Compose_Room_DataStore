package com.example.flightsearch_compose_room_datastore.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.flightsearch_compose_room_datastore.data.AirportCard
import com.example.flightsearch_compose_room_datastore.model.Airport
import com.example.flightsearch_compose_room_datastore.ui.app_flight_screen.FlightList
import com.example.flightsearch_compose_room_datastore.ui.app_search_screen.SearchList
import com.example.flightsearch_compose_room_datastore.ui.app_search_screen.SearchTextField
import com.example.flightsearch_compose_room_datastore.ui.theme.FlightSearch_Compose_Room_DataStoreTheme


@Composable
fun FlightSearchApp(
    searchViewModel: SearchViewModel = viewModel(factory = SearchViewModel.Factory)
) {
    val searchUIState = searchViewModel.uiState.collectAsState().value
    Scaffold(
        topBar = {
            FlightSearchTopAppBar(
                title = "Flight Search",
                modifier = Modifier
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ){
            SearchTextField(
                value = searchUIState.searchField,
                onTextValueChange = {searchViewModel.onSearchChange(it)},
                modifier = Modifier
            )
            Spacer(modifier = Modifier.height(8.dp))
            if (searchUIState.searchField == ""){

            }else{

            }

        }
    }
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


