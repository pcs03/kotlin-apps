package nl.pcstet.navigation.core.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import nl.pcstet.navigation.core.data.local.UserPreferencesDataStore
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

private const val USER_PREFERENCES = "user_preferences"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = USER_PREFERENCES)

val userPreferencesModule = module {
    single<UserPreferencesDataStore> { UserPreferencesDataStore(androidContext().dataStore) }
}