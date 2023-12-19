package com.example.flightsearch_compose_room_datastore.ui.app_search_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
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
    searchViewModel: SearchViewModel = viewModel(factory = SearchViewModel.Factory),
    ) {

    val searchUIState = searchViewModel.uiState.collectAsState().value

    // для того чтобы гарантировать фокус при переходе на экран автоподсказок
    val focusRequester = remember { FocusRequester() }

    Scaffold(
        topBar = {
            FlightSearchTopAppBar(
                title = "Flight Search",
                modifier = Modifier
            )
        }
    ) { innerPadding ->
//         для того чтобы гарантировать фокус при переходе на экран автоподсказок
        LaunchedEffect(searchUIState.searchField) {
            focusRequester.requestFocus()
        }
        Column(
            modifier = modifier.padding(horizontal = 16.dp)
        ) {
            SearchTextField(
                onEraseItemClick = {
                    searchViewModel.updateSearchField("")
                    navigateToFavorite()
                },
                searchFieldValue = searchUIState.searchField,
                onSearchFieldValueChange = {
                    if (it == "") {
                        searchViewModel.updateSearchField(it)
                        navigateToFavorite()
                    } else {
                        searchViewModel.updateSearchField(it)
                    }
                },
                isFilled = searchUIState.searchField!="",
                onSearchFieldClick = {},
                modifier = Modifier
                    .focusRequester(focusRequester) // для того чтобы гарантировать фокус при переходе на экран автоподсказок
                    .fillMaxWidth()
                    .padding(innerPadding)
            )
            Spacer(modifier = Modifier.height(16.dp))
            SearchList(
                onAirportItemClick = {
                    searchViewModel.updateSearchField(it.iataCode)
                    navigateToFlightList()
                },
                airportItemList = searchUIState.airportItemList,
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