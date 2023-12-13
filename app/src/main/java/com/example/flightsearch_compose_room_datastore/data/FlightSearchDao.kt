package com.example.flightsearch_compose_room_datastore.data

import androidx.room.Dao
import androidx.room.Query
import com.example.flightsearch_compose_room_datastore.model.Airport
import com.example.flightsearch_compose_room_datastore.model.Favorite
import kotlinx.coroutines.flow.Flow

@Dao
interface FlightSearchDao {

    @Query("SELECT * from airport WHERE iata_code OR name LIKE :searchField ORDER BY passengers DESC")
    fun getFlightsForSearchField(searchField: String): Flow<List<Airport>>

    @Query("SELECT * from airport WHERE iata_code OR name != :completeSearchField ORDER BY passengers DESC")
    fun getAllFlightsFromChosenAirport(completeSearchField: String): Flow<List<Airport>>

    @Query("SELECT * from favorite ORDER BY id ASC")
    fun getFavoriteFlights(): Flow<List<Favorite>>
}   