package com.example.flightsearch_compose_room_datastore.data

import com.example.flightsearch_compose_room_datastore.model.Airport
import com.example.flightsearch_compose_room_datastore.model.Favorite
import kotlinx.coroutines.flow.Flow

class OfflineFlightRepository(private val flightSearchDao: FlightSearchDao) : FlightRepository{

    override fun getFlightsForSearchField(searchField: String): Flow<List<Airport>> = flightSearchDao.getFlightsForSearchField(searchField)

    override fun getAllFlightsFromChosenAirport(completeSearchField: String): Flow<List<Airport>> = flightSearchDao.getAllFlightsFromChosenAirport(completeSearchField)

    override fun getFavoriteFlights(): Flow<List<Favorite>> = flightSearchDao.getFavoriteFlights()

}