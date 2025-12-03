package nl.pcstet.core.datastore.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import nl.pcstet.core.datastore.DefaultUserPreferencesRepository
import nl.pcstet.core.datastore.UserPreferencesRepository
import nl.pcstet.core.datastore.UserPreferencesSerializer
import nl.pcstet.core.model.UserPreferences
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

private const val USER_PREFERENCES_DATASTORE_FILE_NAME = "user_preferences.json"
private val Context.dataStore: DataStore<UserPreferences> by dataStore(
    fileName = USER_PREFERENCES_DATASTORE_FILE_NAME,
    serializer = UserPreferencesSerializer,
)

val dataStoreModule = module {
    single<DataStore<UserPreferences>> { androidContext().dataStore }
    singleOf(::DefaultUserPreferencesRepository) { bind<UserPreferencesRepository>() }
}