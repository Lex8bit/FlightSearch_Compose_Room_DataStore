package com.example.flightsearch_compose_room_datastore.ui.app_search_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.flightsearch_compose_room_datastore.FlightSearchApplication
import com.example.flightsearch_compose_room_datastore.data.FlightRepository
import com.example.flightsearch_compose_room_datastore.data.UserPreferencesRepository
import com.example.flightsearch_compose_room_datastore.model.Airport
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.transformLatest
import kotlinx.coroutines.launch

data class SearchUiState(
    val searchField : String = "",
    val airportItemList: List<Airport> = listOf(),
)

class SearchViewModel(
    private val flightRepository: FlightRepository,
    private val userPreferencesRepository: UserPreferencesRepository
): ViewModel() {

    //преобразует поток в другой поток, который содержит только самые последние значения из исходного потока.
    @OptIn(ExperimentalCoroutinesApi::class)
    val uiState: StateFlow<SearchUiState> = userPreferencesRepository.searchField
        .flatMapLatest { searchField ->
            flightRepository.getFlightsForSearchFieldFlow(searchField)
                .map { listAirport ->
                    SearchUiState(
                        searchField = searchField,
                        airportItemList = listAirport
                    )
                }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = SearchUiState()
        )
    fun saveSearchInPref(searchQuery: String) {
        viewModelScope.launch {
            userPreferencesRepository.saveSearchField(searchQuery)
        }
    }
    fun saveSearchInPrefAfterErase() {
        viewModelScope.launch {
            userPreferencesRepository.saveSearchField("")
        }
    }

    companion object {
        val Factory : ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as FlightSearchApplication)
                val flightRepository = application.container.flightRepository
                //for sheared pref
                val userPreferencesRepository = application.userPreferencesRepository
                SearchViewModel(flightRepository = flightRepository,userPreferencesRepository = userPreferencesRepository)
            }
        }
    }
}