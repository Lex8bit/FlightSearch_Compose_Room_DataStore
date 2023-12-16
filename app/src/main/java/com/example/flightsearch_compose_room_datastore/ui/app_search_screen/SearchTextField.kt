package com.example.flightsearch_compose_room_datastore.ui.app_search_screen

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
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
    onEraseItemClick : (String)-> Unit,
    searchFieldValue : String,
    onSearchFieldValueChange : (String)->Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        modifier = modifier,
        shape =  RoundedCornerShape(24.dp),
        label = { Text("Enter departure airport") },
        value = searchFieldValue,
        onValueChange = {onSearchFieldValueChange(it)},
        leadingIcon = {
            Icon(
                painter = painterResource(R.drawable.ic_search),
                contentDescription = "Search"
            )
        },
        trailingIcon = {
            IconButton(
                onClick = { onEraseItemClick("") },
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_clear),
                    contentDescription = "Search"
                )
            }

        },
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        ),
        singleLine = true
    )
}

@Preview(showBackground = true)
@Composable
fun SearchTextFieldPreview() {
    FlightSearch_Compose_Room_DataStoreTheme {
        SearchTextField(
            onEraseItemClick = {},
            searchFieldValue = "Enter departure airport",
            onSearchFieldValueChange = {},
            modifier = Modifier.fillMaxWidth()
        )
    }
}