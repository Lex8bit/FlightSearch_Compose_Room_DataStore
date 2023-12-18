package com.example.flightsearch_compose_room_datastore.data

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class UserPreferencesRepository(
    private val dataStore: DataStore<Preferences>
) {
    //Наблюдаем за текущим значением чтобы прочитать
    val searchField: Flow<String> = dataStore.data
        .catch {    //для отлавливания ошибок если вдруг у нас нет файла или хранилище отключено
            if(it is IOException) {
                Log.e(TAG, "Error reading preferences.", it)
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map { preferences ->
            preferences[SEARCH_FIELD] ?: ""
        }

    //Задаем имя ключа
    private companion object {
        val SEARCH_FIELD = stringPreferencesKey("search_field")
        //значение для Log
        const val TAG = "UserPreferencesRepo"
    }
    //Изменяем значение по нашему ключу
    suspend fun saveSearchField(searchField: String) {
        dataStore.edit { preferences ->
           preferences[SEARCH_FIELD] = searchField
        }
    }
}