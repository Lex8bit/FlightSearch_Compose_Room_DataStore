package com.example.flightsearch_compose_room_datastore.ui.app_search_screen

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.flightsearch_compose_room_datastore.model.Airport
import com.example.flightsearch_compose_room_datastore.ui.theme.FlightSearch_Compose_Room_DataStoreTheme

@Composable
fun SearchCard(
    airportInfo: Airport,
    modifier: Modifier = Modifier
){
    Card(
        shape = RoundedCornerShape(0.dp),
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        ){
            Text(
                text = airportInfo.iataCode,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = airportInfo.name,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SearchCardPreview() {
    FlightSearch_Compose_Room_DataStoreTheme {
        SearchCard(
            airportInfo = Airport(0,"FCA","Moscow",15000),
        )
    }
}