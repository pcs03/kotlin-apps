package nl.pcstet.startupflow.data.core.datasource.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import nl.pcstet.startupflow.data.core.datasource.disk.SettingsDataSource
import nl.pcstet.startupflow.data.core.datasource.disk.SettingsDataSourceImpl
import nl.pcstet.startupflow.ui.core.feature.appnav.AppNavViewModel
import nl.pcstet.startupflow.data.core.datasource.network.utils.createHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

private const val SETTINGS_DATASTORE_NAME = "settings_datastore"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = SETTINGS_DATASTORE_NAME)

val coreDataSourceModule = module {
    single<SettingsDataSource> { SettingsDataSourceImpl(androidContext().dataStore) }

    single<HttpClientEngine> { OkHttp.create() }
    single<HttpClient> { createHttpClient(engine = get()) }
}