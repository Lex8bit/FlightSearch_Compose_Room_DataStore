package com.example.flightsearch_compose_room_datastore

import android.app.Application
import com.example.flightsearch_compose_room_datastore.di.AppContainer
import com.example.flightsearch_compose_room_datastore.di.AppDataContainer


class FlightSearchApplication : Application() {

    /**
     * AppContainer instance used by the rest of classes to obtain dependencies
     */
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}