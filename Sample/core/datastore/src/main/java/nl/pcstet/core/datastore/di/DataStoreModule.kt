package nl.pcstet.core.datastore.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import nl.pcstet.core.datastore.PreferencesDataSource
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

private const val PREFERENCES_DATASTORE_NAME = "settings_datastore"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PREFERENCES_DATASTORE_NAME)

val dataStoreModule = module {
    single<PreferencesDataSource> { PreferencesDataSource(androidContext().dataStore) }
}