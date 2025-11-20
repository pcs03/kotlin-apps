package nl.pcstet.core.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PreferencesDataSource(
    private val dataStore: DataStore<Preferences>
) {
    companion object {
        private val HAS_USER_LOGGED_IN_OR_CREATED_ACCOUNT_KEY =
            booleanPreferencesKey("HAS_USER_LOGGED_IN_OR_CREATED_ACCOUNT")
        private val EMAIL_ADDRESS_KEY = stringPreferencesKey("EMAIL_ADDRESS")
        private val ACCESS_TOKEN_KEY = stringPreferencesKey("ACCESS_TOKEN")
        private val API_URL_KEY = stringPreferencesKey("API_URL")
    }

    val hasUserLoggedInOrCreatedAccount: Flow<Boolean> =
        dataStore.data.map { preferences ->
            preferences[HAS_USER_LOGGED_IN_OR_CREATED_ACCOUNT_KEY] ?: false
        }

    val emailAddress: Flow<String?> = dataStore.data.map { preferences ->
        preferences[EMAIL_ADDRESS_KEY]
    }

    val apiUrl: Flow<String?> = dataStore.data.map { preferences ->
        preferences[API_URL_KEY]
    }

    val accessToken: Flow<String?> = dataStore.data.map { preferences ->
        preferences[ACCESS_TOKEN_KEY]
    }

    suspend fun setUserLoggedInOrCreatedAccount(value: Boolean) {
        dataStore.edit { preferences ->
            preferences[HAS_USER_LOGGED_IN_OR_CREATED_ACCOUNT_KEY] = value
        }
    }

    suspend fun clearUserLoggedInOrCreatedAccount() {
        dataStore.edit { preferences ->
            preferences.remove(HAS_USER_LOGGED_IN_OR_CREATED_ACCOUNT_KEY)
        }
    }

    suspend fun setEmailAddress(value: String) {
        dataStore.edit { preferences ->
            preferences[EMAIL_ADDRESS_KEY] = value
        }
    }

    suspend fun clearEmailAddress() {
        dataStore.edit { preferences ->
            preferences.remove(EMAIL_ADDRESS_KEY)
        }
    }

    suspend fun setApiUrl(value: String) {
        dataStore.edit { preferences ->
            preferences[API_URL_KEY] = value
        }
    }

    suspend fun clearApiUrl() {
        dataStore.edit { preferences ->
            preferences.remove(API_URL_KEY)
        }
    }

    suspend fun setAccessToken(value: String) {
        dataStore.edit { preferences ->
            preferences[ACCESS_TOKEN_KEY] = value
        }
    }

    suspend fun clearAccessToken() {
        dataStore.edit { preferences ->
            preferences.remove(ACCESS_TOKEN_KEY)
        }
    }
}
