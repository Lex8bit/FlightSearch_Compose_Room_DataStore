package com.example.flightsearch_compose_room_datastore.ui.app_flight_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.flightsearch_compose_room_datastore.R
import com.example.flightsearch_compose_room_datastore.data.AirportCard
import com.example.flightsearch_compose_room_datastore.ui.FlightSearchUiState
import com.example.flightsearch_compose_room_datastore.ui.app_search_screen.SearchTextField
import com.example.flightsearch_compose_room_datastore.ui.navigation.NavigationDestination
import com.example.flightsearch_compose_room_datastore.ui.theme.FlightSearch_Compose_Room_DataStoreTheme


object FlightResultDestination : NavigationDestination {
    override val route = "home"
}

@Composable
fun FlightResultScreen(
    value: String,
    onTextValueChange : (String)-> Unit,
    airportCardList : List<AirportCard>,
    onStarClick : () -> Unit,
    uiState : FlightSearchUiState,
    modifier : Modifier = Modifier
){
    Column (
        modifier = modifier
    ){
        SearchTextField(
            value = value,
            onTextValueChange = onTextValueChange,
            modifier = Modifier.fillMaxWidth()
        )
        FlightList(
            airportCardList = airportCardList,
            onStarClick = onStarClick,
            uiState = uiState,
            modifier = Modifier.padding(vertical = 4.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun FlightResultScreenPreview() {
    FlightSearch_Compose_Room_DataStoreTheme {
        FlightResultScreen(
            value = "Enter departure airport",
            onTextValueChange = {},
            airportCardList = listOf(
                AirportCard(0,"SPb", "Sait-Petersburg", "MSK", "Moscow"),
                AirportCard(1,"NSB", "Novosibirsk", "VDK", "Vladivostok")
            ),
            onStarClick = {},
            uiState = FlightSearchUiState()
        )
    }
}