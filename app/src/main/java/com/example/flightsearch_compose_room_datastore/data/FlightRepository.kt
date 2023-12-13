package com.example.flightsearch_compose_room_datastore.data

import androidx.room.Query
import com.example.flightsearch_compose_room_datastore.model.Airport
import com.example.flightsearch_compose_room_datastore.model.Favorite
import kotlinx.coroutines.flow.Flow

interface FlightRepository {

    fun getFlightsForSearchField(searchField: String): Flow<List<Airport>>

    fun getAllFlightsFromChosenAirport(completeSearchField: String): Flow<List<Airport>>

    fun getFavoriteFlights(): Flow<List<Favorite>>
}