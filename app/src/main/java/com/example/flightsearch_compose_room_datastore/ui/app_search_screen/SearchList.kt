package com.example.flightsearch_compose_room_datastore.ui.app_search_screen

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.flightsearch_compose_room_datastore.model.Airport
import com.example.flightsearch_compose_room_datastore.ui.theme.FlightSearch_Compose_Room_DataStoreTheme



@Composable
fun SearchList(
    onAirportItemClick : (Airport)->Unit,
    airportItemList : List<Airport>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
    ){
        items(airportItemList){ airportItem ->
            SearchCard(
                onAirportItemClick = onAirportItemClick,
                airportItem = airportItem,
                modifier = Modifier
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SearchListPreview() {
    FlightSearch_Compose_Room_DataStoreTheme {
        SearchList(
            onAirportItemClick = {},
            airportItemList = listOf(
                Airport(0,"FFF","Airport Saint-Petersburg",555),
                Airport(0,"FAD","Airport NMPSADF",555),
                Airport(0,"BBB","Airport fdfadfFAFAFADFDFEF",555),
                Airport(0,"AVF","Airport FLSKNGSLGN",555),
            )
        )
    }
}