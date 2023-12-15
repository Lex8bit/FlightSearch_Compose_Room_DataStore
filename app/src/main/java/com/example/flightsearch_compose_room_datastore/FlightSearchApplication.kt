package com.example.flightsearch_compose_room_datastore

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.flightsearch_compose_room_datastore.data.UserPreferencesRepository
import com.example.flightsearch_compose_room_datastore.di.AppContainer
import com.example.flightsearch_compose_room_datastore.di.AppDataContainer


//Имя для DataStore
//for ShearedPref
private const val SEARCH_FIELD_PREFERENCE_NAME = "search_field_preferences"

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = SEARCH_FIELD_PREFERENCE_NAME
)

class FlightSearchApplication : Application() {

    //for ShearedPref
    lateinit var userPreferencesRepository: UserPreferencesRepository

    /**
     * AppContainer instance used by the rest of classes to obtain dependencies
     * For DI Room
     */
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        //di for room
        container = AppDataContainer(this)
        //for ShearedPref
        userPreferencesRepository = UserPreferencesRepository(dataStore)
    }
}