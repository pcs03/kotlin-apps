package nl.pcstet.startupflow.data.core.datasource.disk

import kotlinx.coroutines.flow.Flow

interface SettingsDataSource {
    val hasUserLoggedInOrCreatedAccount: Flow<Boolean>
    val emailAddress: Flow<String?>
    val apiUrl: Flow<String?>
    val accessToken: Flow<String?>

    suspend fun setUserLoggedInOrCreatedAccount(value: Boolean)
    suspend fun clearUserLoggedInOrCreatedAccount()

    suspend fun setEmailAddress(value: String)
    suspend fun clearEmailAddress()

    suspend fun setApiUrl(value: String)
    suspend fun clearApiUrl()

    suspend fun setAccessToken(value: String)
    suspend fun clearAccessToken()
}
