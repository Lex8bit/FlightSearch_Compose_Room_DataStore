package com.example.flightsearch_compose_room_datastore.ui.app_flight_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.flightsearch_compose_room_datastore.FlightSearchApplication
import com.example.flightsearch_compose_room_datastore.data.AirportCard
import com.example.flightsearch_compose_room_datastore.data.FlightRepository
import com.example.flightsearch_compose_room_datastore.data.UserPreferencesRepository
import com.example.flightsearch_compose_room_datastore.model.Airport
import com.example.flightsearch_compose_room_datastore.model.Favorite
import com.example.flightsearch_compose_room_datastore.ui.app_favorite_screen.toFavorite
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


data class FlightUiState(
    val searchField : String = "",
    val airportCards : List<AirportCard> = listOf(),
    val tableName : String = "",
)
class FlightViewModel(
    private val flightRepository: FlightRepository,
    private val userPreferencesRepository: UserPreferencesRepository
): ViewModel() {
//    private val _uiState = MutableStateFlow(FlightUiState())
//    val uiState: StateFlow<FlightUiState> = _uiState.asStateFlow()

    //Shared Pref
//     один поток FavoriteUiState получился путем объединения airportCardsFLOW searchFieldFLOW
    val uiState: StateFlow<FlightUiState> = userPreferencesRepository.searchField
        .flatMapLatest { searchField ->
            combine(
                flightRepository.getAllFlightsFromChosenAirportFlow(searchField),
                flightRepository.getFavoriteFlightsFlow()
            ) { allFlightsFromChosenAirport, favoriteFlights ->
                FlightUiState(
                    searchField = searchField,
                    airportCards = allFlightsFromChosenAirport.map {airport ->
                        val departureItem =
                            withContext(Dispatchers.IO) {flightRepository.getAirportNameByCode(searchField)}
                        val isFavorite = favoriteFlights.contains(
                                Favorite(
                                    airport.id,
                                    departureItem.iataCode,
                                    airport.iataCode
                                )
                            )
                        airport.toAirportCard(departureItem.iataCode, departureItem.name,isFavorite)
                    },
                    tableName = "Search from $searchField"
                )
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = FlightUiState()
        )


    fun saveSearchInPref(searchQuery: String) {
        viewModelScope.launch {
            userPreferencesRepository.saveSearchField(searchQuery)
        }
    }

    fun saveOrDeleteInFavorites(item: AirportCard) {
        viewModelScope.launch {
            if (item.isFavourite){
                flightRepository.deleteFromFavorites(item.toFavorite())
            }else{
                flightRepository.insertInFavorite(item.toFavorite())
            }
        }
    }

    companion object {
        val Factory : ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as FlightSearchApplication)
                val flightRepository = application.container.flightRepository
                //for sheared pref
                val userPreferencesRepository = application.userPreferencesRepository
                FlightViewModel(flightRepository = flightRepository,userPreferencesRepository = userPreferencesRepository)
            }
        }
    }
}

fun Airport.toAirportCard(departureCode:String,departureName:String,isFavorite:Boolean): AirportCard {
    return AirportCard(
        id = id,
        isFavourite = isFavorite,
        departureName = departureName,
        destinationName = name,
        iataDepartureCode = departureCode,
        iataDestinationCode = iataCode,
    )
}