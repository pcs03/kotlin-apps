package nl.pcstet.navigation.main.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreferencesDataStore(private val dataStore: DataStore<Preferences>) {

    companion object {
        val AUTH_TOKEN_KEY = stringPreferencesKey("AUTH_TOKEN")
        val ONBOARDING_REQUIRED_KEY = booleanPreferencesKey("ONBOARDING_REQUIRED")
        val BACKEND_URI_KEY = stringPreferencesKey("BACKEND_URI")
    }

    val authToken: Flow<String?> = dataStore.data.map { preferences ->
        preferences[AUTH_TOKEN_KEY]
    }

    suspend fun saveAuthToken(token: String) {
        dataStore.edit { preferences ->
            preferences[AUTH_TOKEN_KEY] = token
        }
    }

    suspend fun clearAuthToken() {
        dataStore.edit { preferences ->
            preferences.remove(AUTH_TOKEN_KEY)
        }
    }

    val onboardingRequired: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[ONBOARDING_REQUIRED_KEY] ?: false
    }

    suspend fun saveOnboardingRequired(onBoardingRequired: Boolean) {
        dataStore.edit { preferences ->
            preferences[ONBOARDING_REQUIRED_KEY] = onBoardingRequired
        }
    }

    val backendUri: Flow<String?> = dataStore.data.map { preferences ->
        preferences[BACKEND_URI_KEY]
//            ?: "https://dummyjson.com"
    }

    suspend fun setBackendUri(backendUri: String) {
        dataStore.edit { preferences ->
            preferences[BACKEND_URI_KEY] = backendUri
        }
    }

    suspend fun removeBackendUri() {
        dataStore.edit { preferences ->
            preferences.remove(BACKEND_URI_KEY)
        }
    }
}