package com.example.flightsearch_compose_room_datastore.data

import com.example.flightsearch_compose_room_datastore.model.Airport
import com.example.flightsearch_compose_room_datastore.model.Favorite
import kotlinx.coroutines.flow.Flow

interface FlightRepository {

    fun getFlightsForSearchFieldFlow( searchField: String): Flow<List<Airport>> //List<Airport>

    fun getAllFlightsFromChosenAirportFlow(completeSearchField: String): Flow<List<Airport>>

    /**
     * Взять все любимы направления полетов
     */
    fun getFavoriteFlightsFlow(): Flow<List<Favorite>>

    /**
     * Удаление записей из Таблицы Favorite
     */
    suspend fun deleteFromFavorites(departureCode: String, destinationCode: String)//(item:Favorite)

    /**
     * Получить имена аэропортов по коду / для Favorite
     */
    suspend fun getAirportItemByCode(code: String): Airport?

    /**
     *Сохранить в таблицу Favorites
     */
    suspend fun insertInFavorite(departureCode: String, destinationCode: String)//(item: Favorite)
}