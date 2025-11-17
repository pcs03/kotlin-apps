package nl.pcstet.startupflow.data.core.datasource.disk

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingsDataStore(private val dataStore: DataStore<Preferences>) {
    companion object {
        private val AUTH_TOKEN_KEY = stringPreferencesKey("AUTH_TOKEN")
        private val API_URL_KEY = stringPreferencesKey("API_URL")
    }

    val accessToken: Flow<String?> = dataStore.data.map { preferences ->
        preferences[AUTH_TOKEN_KEY]
    }

    suspend fun saveAccessToken(token: String) {
        dataStore.edit { preferences ->
            preferences[AUTH_TOKEN_KEY] = token
        }
    }

    suspend fun clearAccessToken() {
        dataStore.edit { preferences ->
            preferences.remove(AUTH_TOKEN_KEY)
        }
    }

    val apiUrl: Flow<String?> = dataStore.data.map { preferences ->
        preferences[API_URL_KEY]
    }

    suspend fun setApiUrl(apiUrl: String) {
        dataStore.edit { preferences ->
            preferences[API_URL_KEY] = apiUrl
        }
    }

    suspend fun clearApiUrl() {
        dataStore.edit { preferences ->
            preferences.remove(API_URL_KEY)
        }
    }
}