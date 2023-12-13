package com.example.flightsearch_compose_room_datastore.ui.app_search_screen

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.flightsearch_compose_room_datastore.R
import com.example.flightsearch_compose_room_datastore.ui.theme.FlightSearch_Compose_Room_DataStoreTheme

@Composable
fun SearchTextField(
    value : String,
    onTextValueChange : (String)->Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        modifier = modifier,
        shape =  RoundedCornerShape(24.dp),
        value = value,
        onValueChange = {onTextValueChange(it)},
        leadingIcon = {
            Icon(
                painter = painterResource(R.drawable.ic_search),
                contentDescription = "Search"
            )
        },
        trailingIcon = {
            Icon(
                painter = painterResource(R.drawable.ic_mic),
                contentDescription = "Search"
            )
        },
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        )
    )
}

@Preview(showBackground = true)
@Composable
fun SearchTextFieldPreview() {
    FlightSearch_Compose_Room_DataStoreTheme {
        SearchTextField(
            value = "Enter departure airport",
            onTextValueChange = {},
            modifier = Modifier.fillMaxWidth()
        )
    }
}