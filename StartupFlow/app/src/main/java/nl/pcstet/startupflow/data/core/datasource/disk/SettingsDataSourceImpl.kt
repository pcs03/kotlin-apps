package nl.pcstet.startupflow.data.core.datasource.disk

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class SettingsDataSourceImpl(
    private val dataStore: DataStore<Preferences>
) : SettingsDataSource {
    companion object {
        private val HAS_USER_LOGGED_IN_OR_CREATED_ACCOUNT_KEY =
            booleanPreferencesKey("HAS_USER_LOGGED_IN_OR_CREATED_ACCOUNT")
        private val EMAIL_ADDRESS_KEY = stringPreferencesKey("EMAIL_ADDRESS")
        private val ACCESS_TOKEN_KEY = stringPreferencesKey("ACCESS_TOKEN")
        private val API_URL_KEY = stringPreferencesKey("API_URL")
    }

    override val hasUserLoggedInOrCreatedAccount: Flow<Boolean> =
        dataStore.data.map { preferences ->
            preferences[HAS_USER_LOGGED_IN_OR_CREATED_ACCOUNT_KEY] ?: false
        }

    override val emailAddress: Flow<String?> = dataStore.data.map { preferences ->
        preferences[EMAIL_ADDRESS_KEY]
    }

    override val apiUrl: Flow<String?> = dataStore.data.map { preferences ->
        preferences[API_URL_KEY]
    }

    override val accessToken: Flow<String?> = dataStore.data.map { preferences ->
        preferences[ACCESS_TOKEN_KEY]
    }

    override suspend fun setUserLoggedInOrCreatedAccount(value: Boolean) {
        dataStore.edit { preferences ->
            preferences[HAS_USER_LOGGED_IN_OR_CREATED_ACCOUNT_KEY] = value
        }
    }

    override suspend fun clearUserLoggedInOrCreatedAccount() {
        dataStore.edit { preferences ->
            preferences.remove(HAS_USER_LOGGED_IN_OR_CREATED_ACCOUNT_KEY)
        }
    }

    override suspend fun setEmailAddress(value: String) {
        dataStore.edit { preferences ->
            preferences[EMAIL_ADDRESS_KEY] = value
        }
    }

    override suspend fun clearEmailAddress() {
        dataStore.edit { preferences ->
            preferences.remove(EMAIL_ADDRESS_KEY)
        }
    }

    override suspend fun setApiUrl(value: String) {
        dataStore.edit { preferences ->
            preferences[API_URL_KEY] = value
        }
    }

    override suspend fun clearApiUrl() {
        dataStore.edit { preferences ->
            preferences.remove(API_URL_KEY)
        }
    }

    override suspend fun setAccessToken(value: String) {
        dataStore.edit { preferences ->
            preferences[ACCESS_TOKEN_KEY] = value
        }
    }

    override suspend fun clearAccessToken() {
        dataStore.edit { preferences ->
            preferences.remove(ACCESS_TOKEN_KEY)
        }
    }


}