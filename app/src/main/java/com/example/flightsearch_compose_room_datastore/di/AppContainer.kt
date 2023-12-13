package com.example.flightsearch_compose_room_datastore.di

import android.content.Context
import com.example.flightsearch_compose_room_datastore.data.FlightRepository
import com.example.flightsearch_compose_room_datastore.data.FlightSearchDatabase
import com.example.flightsearch_compose_room_datastore.data.OfflineFlightRepository

/**
 * App container for Dependency injection.
 */
interface AppContainer {
    val flightRepository: FlightRepository
}

/**
 * [AppContainer] implementation that provides instance of [OfflineFlightRepository]
 */
class AppDataContainer(private val context: Context) : AppContainer {
    /**
     * Implementation for [FlightRepository]
     */
    override val flightRepository: FlightRepository by lazy {
        OfflineFlightRepository(FlightSearchDatabase.getDatabase(context).flightSearchDao())    }
}