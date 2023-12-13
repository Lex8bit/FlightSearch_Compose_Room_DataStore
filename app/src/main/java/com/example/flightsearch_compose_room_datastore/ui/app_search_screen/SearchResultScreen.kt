package com.example.flightsearch_compose_room_datastore.ui.app_search_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.flightsearch_compose_room_datastore.R
import com.example.flightsearch_compose_room_datastore.model.Airport
import com.example.flightsearch_compose_room_datastore.ui.navigation.NavigationDestination
import com.example.flightsearch_compose_room_datastore.ui.theme.FlightSearch_Compose_Room_DataStoreTheme



@Composable
fun SearchResultScreen(
    value: String,
    onTextValueChange : (String)-> Unit,
    airportInfoList : List<Airport>,
    modifier : Modifier = Modifier
){
    Column(
        modifier = modifier
    ) {
        SearchTextField(
            value = value,
            onTextValueChange = onTextValueChange,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        SearchList(
            airportInfoList  = airportInfoList,
            modifier = Modifier
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SearchResultScreenPreview() {
    FlightSearch_Compose_Room_DataStoreTheme {
        SearchResultScreen(
            value = "Enter departure airport",
            onTextValueChange = {},
            airportInfoList = listOf(
                Airport(0, "FFF", "Airport Saint-Petersburg", 555),
                Airport(0, "FAD", "Airport NMPSADF", 555),
                Airport(0, "BBB", "Airport fdfadfFAFAFADFDFEF", 555),
                Airport(0, "AVF", "Airport FLSKNGSLGN", 555),
            )
        )
    }
}