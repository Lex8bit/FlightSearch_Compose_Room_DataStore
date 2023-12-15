package com.example.flightsearch_compose_room_datastore.ui.app_flight_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.flightsearch_compose_room_datastore.data.AirportCard
import com.example.flightsearch_compose_room_datastore.ui.theme.FlightSearch_Compose_Room_DataStoreTheme

@Composable
fun FlightList(
    tableName : String,
    airportCardList : List<AirportCard>,
    onAirportCardItemStarClick : (AirportCard) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = tableName,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            modifier = Modifier.padding(vertical = 16.dp)
        )
        LazyColumn(
            contentPadding = PaddingValues(0.dp)
        ){
            items(airportCardList){ airportCardItem ->
                FlightCard(
                    airportCardItem = airportCardItem,
                    onAirportCardItemStarClick = onAirportCardItemStarClick,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FlightListPreview() {
    FlightSearch_Compose_Room_DataStoreTheme {
        FlightList(
            airportCardList = listOf(
                AirportCard(0, "SPb", "Sait-Petersburg", "MSK", "Moscow"),
                AirportCard(1, "NSB", "Novosibirsk", "VDK", "Vladivostok")
            ),
            onAirportCardItemStarClick = {},
            tableName = "No Favorites",
        )
    }
}
