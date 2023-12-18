package com.example.flightsearch_compose_room_datastore.data

import com.example.flightsearch_compose_room_datastore.model.Airport
import com.example.flightsearch_compose_room_datastore.model.Favorite
import kotlinx.coroutines.flow.Flow

class OfflineFlightRepository(private val flightSearchDao: FlightSearchDao) : FlightRepository{
    override fun getFlightsForSearchFieldFlow(searchField: String): List<Airport> = flightSearchDao.getFlightsForSearchFieldFlow(searchField)
    override fun getAllFlightsFromChosenAirportFlow(completeSearchField: String): Flow<List<Airport>> = flightSearchDao.getAllFlightsFromChosenAirportFlow(completeSearchField)
    override fun getFavoriteFlightsFlow(): Flow<List<Favorite>> = flightSearchDao.getFavoriteFlightsFlow()
    override suspend fun deleteFromFavorites(departureCode: String, destinationCode: String) = flightSearchDao.deleteFromFavorites(departureCode,destinationCode)
    override suspend fun getAirportItemByCode(code: String): Airport? = flightSearchDao.getAirportItemByCode(code)
    override suspend fun insertInFavorite(departureCode: String, destinationCode: String) = flightSearchDao.insertInFavorite(departureCode,destinationCode)

}