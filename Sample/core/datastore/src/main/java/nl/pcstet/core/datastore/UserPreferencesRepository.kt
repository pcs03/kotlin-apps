package nl.pcstet.core.datastore

import kotlinx.coroutines.flow.Flow
import nl.pcstet.core.model.AppTheme
import nl.pcstet.core.model.UserPreferences

interface UserPreferencesRepository {
    val userPreferences: Flow<UserPreferences>

    suspend fun updateApiUrl(apiUrl: String)
    suspend fun updateOnboardingComplete(completed: Boolean)
    suspend fun saveAuthData(accessToken: String, email: String, rememberEmail: Boolean)
    suspend fun clearAuthData()
    suspend fun updateAppTheme(appTheme: AppTheme)
}