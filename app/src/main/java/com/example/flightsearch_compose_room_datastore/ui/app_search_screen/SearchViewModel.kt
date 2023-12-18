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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class SearchUiState(
    val searchField : String = "",
    val airportItemList: List<Airport> = listOf(),
)

class SearchViewModel(
    private val flightRepository: FlightRepository,
    private val userPreferencesRepository: UserPreferencesRepository
): ViewModel() {

    private val _uiState = MutableStateFlow(SearchUiState())
    var uiState :StateFlow<SearchUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO){
            userPreferencesRepository.searchField.collect{
                updateState(it)
            }
        }
    }

    private fun updateState(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val list = flightRepository.getFlightsForSearchFieldFlow(query)
            _uiState.update {
                it.copy(
                    searchField = query,
                    airportItemList = list
                )
            }
        }
    }
    fun updateSearchField(searchQuery: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val list = flightRepository.getFlightsForSearchFieldFlow(searchQuery)
            _uiState.update {
                it.copy(
                    searchField = searchQuery,
                    airportItemList = list
                )
            }
        }
        saveSearchInPref(searchQuery)
    }

//    @OptIn(ExperimentalCoroutinesApi::class)
//    val uiState: StateFlow<SearchUiState> = userPreferencesRepository.searchField
//        .flatMapLatest { searchField ->
//            flightRepository.getFlightsForSearchFieldFlow(searchField)
//                .map { listAirport ->
//                    SearchUiState(
//                        searchField = searchField,
//                        airportItemList = listAirport
//                    )
//                }
//        }
//        .stateIn(
//            scope = viewModelScope,
//            started = SharingStarted.WhileSubscribed(5_000),
//            initialValue = SearchUiState()
//        )
    private fun saveSearchInPref(searchQuery: String) {
        viewModelScope.launch(Dispatchers.IO){
            userPreferencesRepository.saveSearchField(searchQuery)
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