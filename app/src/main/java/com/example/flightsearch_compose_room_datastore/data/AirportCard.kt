package com.example.flightsearch_compose_room_datastore.data

/**
 * Дата Класс для отображения его во View
 */
data class AirportCard(
    val id : Int = 0,
    val iataDepartureCode: String = "",
    val departureName: String = "",
    val iataDestinationCode: String = "",
    val destinationName: String = "",
    val isFavourite: Boolean = false
)