package nl.pcstet.navigation.main.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import nl.pcstet.navigation.main.data.local.UserPreferencesDataStore
import nl.pcstet.navigation.main.presentation.RootViewModel
import nl.pcstet.navigation.main.presentation.MainViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

private const val USER_PREFERENCES = "user_preferences"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = USER_PREFERENCES)

val mainAppModule = module {
    single<UserPreferencesDataStore> { UserPreferencesDataStore(androidContext().dataStore) }

    viewModelOf(::MainViewModel)
    viewModelOf(::RootViewModel)
}

