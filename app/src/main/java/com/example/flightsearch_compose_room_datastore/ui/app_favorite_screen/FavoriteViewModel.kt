package com.example.flightsearch_compose_room_datastore.ui.app_favorite_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.flightsearch_compose_room_datastore.FlightSearchApplication
import com.example.flightsearch_compose_room_datastore.data.AirportCard
import com.example.flightsearch_compose_room_datastore.data.FlightRepository
import com.example.flightsearch_compose_room_datastore.data.UserPreferencesRepository
import com.example.flightsearch_compose_room_datastore.model.Favorite
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

data class FavoriteUiState(
    val searchField : String = "",
    val airportCards : List<AirportCard> = listOf(),
    val tableName : String = "",
    )
class FavoriteViewModel(
    private val flightRepository: FlightRepository,
    private val userPreferencesRepository: UserPreferencesRepository
): ViewModel() {

    val uiState: StateFlow<FavoriteUiState> =
        flightRepository.getFavoriteFlightsFlow().map { listFavorite ->
            FavoriteUiState(
                airportCards = listFavorite.map {
                    //Делаем запрос к БД для определения имен аэропортов
                    val departureItem = withContext(Dispatchers.IO) {
                        async { flightRepository.getAirportItemByCode(it.departureCode) }
                    }
                    val destinationItem = withContext(Dispatchers.IO) {
                        async { flightRepository.getAirportItemByCode(it.destinationCode) }
                    }
                    it.toAirportCard(departureItem.await()!!.name, destinationItem.await()!!.name)
                },
                tableName = if (listFavorite.isEmpty()) "No Favorite Routes" else "Favorite routes"
            )
        }
            //для преобразования Flow в StateFlow.
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = FavoriteUiState()
            )


    //    val uiState: StateFlow<FavoriteUiState> =
//        flightRepository.getFavoriteFlightsFlow().map { listFavorite ->
//            FavoriteUiState(
//                airportCards = listFavorite.map {
//                    var departureItem = Airport()
//                    var destinationItem = Airport()
//                    viewModelScope.launch(Dispatchers.IO) {
//                        departureItem = flightRepository.getAirportItemByCode(it.departureCode)?:Airport()
//                        destinationItem = flightRepository.getAirportItemByCode(it.destinationCode)?:Airport()
//                    }
//                    it.toAirportCard(departureItem.name, destinationItem.name)
//                },
//                tableName = if (listFavorite.isEmpty()) "No Favorite Routes" else "Favorite routes"
//            )
//        }
//            //для преобразования Flow в StateFlow.
//            .stateIn(
//                scope = viewModelScope,
//                started = SharingStarted.WhileSubscribed(5_000),
//                initialValue = FavoriteUiState()
//            )

    fun onStarClick(item:AirportCard) {
        viewModelScope.launch {
            flightRepository.deleteFromFavorites(item.iataDepartureCode,item.iataDestinationCode)
        }
    }
    companion object {
        val Factory : ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as FlightSearchApplication)
                val flightRepository = application.container.flightRepository
                //for sheared pref
                val userPreferencesRepository = application.userPreferencesRepository
                FavoriteViewModel(flightRepository = flightRepository, userPreferencesRepository = userPreferencesRepository)
            }
        }
    }
}

fun Favorite.toAirportCard(departureName:String, destinationName:String): AirportCard {
    Log.d("my","toAirportCard is working")
    return AirportCard(
//        id = id,
        isFavourite = true,
        departureName = departureName,
        destinationName = destinationName,
        iataDepartureCode = departureCode,
        iataDestinationCode = destinationCode,
    )
}