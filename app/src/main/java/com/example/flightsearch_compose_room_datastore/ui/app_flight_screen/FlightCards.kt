package com.example.flightsearch_compose_room_datastore.ui.app_flight_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.flightsearch_compose_room_datastore.R
import com.example.flightsearch_compose_room_datastore.data.AirportCard
import com.example.flightsearch_compose_room_datastore.ui.FlightSearchUiState
import com.example.flightsearch_compose_room_datastore.ui.theme.FlightSearch_Compose_Room_DataStoreTheme

@Composable
fun FlightCard(
    airportCard : AirportCard,
    onStarClick : () -> Unit,
    uiState : FlightSearchUiState,
    modifier: Modifier = Modifier
){
    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        shape = RoundedCornerShape(
            topStart = 0.dp,
            topEnd = 24.dp,
            bottomEnd = 0.dp,
            bottomStart = 0.dp
        ),
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.padding_medium))
        ) {
            Column (
                modifier = Modifier
                    .weight(5f)
            ){
                Text(
                    text = "DEPART",
                    textAlign = TextAlign.Start,
                )
                Row(
                    modifier = Modifier
                        .padding(
                            top = 4.dp
                        )
                ){
                    Text(
                        text = airportCard.iataDepartureCode ,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(
                        modifier = Modifier
                            .width(16.dp)
                    )
                    Text(
                        text = airportCard.departureName,
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "ARRIVE",
                    textAlign = TextAlign.Start
                )
                Row(
                    modifier = Modifier
                        .padding(
                            top = 4.dp
                        )
                ){
                    Text(
                        text = airportCard.iataDestinationCode,
                        fontWeight = FontWeight.Bold

                    )
                    Spacer(
                        modifier = Modifier
                            .width(16.dp)
                    )
                    Text(
                        text = airportCard.destinationName,
                    )
                }
            }
            IconButton(
                onClick = onStarClick,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                Icon(
                    painter = painterResource(uiState.starIcon),
                    contentDescription = "Select as favorite"
                )
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun FlightCardPreview() {
    FlightSearch_Compose_Room_DataStoreTheme {
        FlightCard(
            airportCard = AirportCard(0,"SPb","Sait-Petersburg","MSK","Moscow"),
            onStarClick = {},
            uiState = FlightSearchUiState()
            )
    }
}
