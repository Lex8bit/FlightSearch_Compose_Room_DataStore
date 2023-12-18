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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
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

//     Shared Pref один поток FavoriteUiState получился путем объединения airportCardsFLOW searchFieldFLOW
@OptIn(ExperimentalCoroutinesApi::class)
val uiState: StateFlow<FlightUiState> = userPreferencesRepository.searchField
        .flatMapLatest { searchField ->
            var departureItem = withContext(Dispatchers.IO) {
                async { flightRepository.getAirportItemByCode(searchField)?: Airport() }
            }
            combine(
                flightRepository.getAllFlightsFromChosenAirportFlow(searchField),
                flightRepository.getFavoriteFlightsFlow()
            ) { allFlightsFromChosenAirport, favoriteFlights ->
                FlightUiState(
                    searchField = searchField,
                    airportCards = allFlightsFromChosenAirport.map {airport ->
                        val isFavorite =
                            favoriteFlights.any { favorite ->
                                favorite.departureCode == departureItem.await().iataCode && favorite.destinationCode == airport.iataCode
                            }
                        airport.toAirportCard(departureItem.await().iataCode, departureItem.await().name,isFavorite)
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

    fun saveSearchInPrefAfterErase() {
        viewModelScope.launch(Dispatchers.IO){
            userPreferencesRepository.saveSearchField("")
        }
    }

    fun saveOrDeleteInFavorites(item: AirportCard) {
        viewModelScope.launch {
            if (item.isFavourite){
                flightRepository.deleteFromFavorites(item.iataDepartureCode,item.iataDestinationCode)
            }else{
                flightRepository.insertInFavorite(item.iataDepartureCode,item.iataDestinationCode)
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
//        id = id,
        isFavourite = isFavorite,
        departureName = departureName,
        destinationName = name,
        iataDepartureCode = departureCode,
        iataDestinationCode = iataCode,
    )
}