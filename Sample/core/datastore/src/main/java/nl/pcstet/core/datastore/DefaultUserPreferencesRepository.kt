package nl.pcstet.core.datastore

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import nl.pcstet.core.model.AppTheme
import nl.pcstet.core.model.UserPreferences

class DefaultUserPreferencesRepository(
    private val dataStore: DataStore<UserPreferences>,
) : UserPreferencesRepository {
    override val userPreferences: Flow<UserPreferences> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
//                Log.e("UserPreferencesRepository", "${exception.message}")
                throw exception // TODO:
            } else {
                throw exception
            }
        }

    override suspend fun updateApiUrl(apiUrl: String) {
        dataStore.updateData { preferences ->
            preferences.copy(apiUrl = apiUrl)
        }
    }

    override suspend fun updateOnboardingComplete(completed: Boolean) {
        dataStore.updateData { preferences ->
            preferences.copy(onboardingComplete = completed)
        }
    }

    override suspend fun saveAuthData(
        accessToken: String,
        email: String,
        rememberEmail: Boolean
    ) {
        dataStore.updateData { preferences ->
            preferences.copy(
                accessToken = accessToken,
                rememberedEmail = if (rememberEmail) email else null,
            )
        }
    }

    override suspend fun clearAuthData() {
        dataStore.updateData { preferences ->
            preferences.copy(
                accessToken = null,
            )
        }
    }

    override suspend fun updateAppTheme(appTheme: AppTheme) {
        dataStore.updateData { preferences ->
            preferences.copy(appTheme = appTheme)
        }
    }
}