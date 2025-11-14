package nl.pcstet.navigation.main.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import nl.pcstet.navigation.main.data.network.ApiClientHolder
import nl.pcstet.navigation.main.data.local.UserPreferencesDataStore
import nl.pcstet.navigation.main.presentation.MainViewModel
import nl.pcstet.navigation.main.presentation.RootViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

private const val USER_PREFERENCES = "user_preferences"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = USER_PREFERENCES)

val mainAppModule = module {
    single<UserPreferencesDataStore> { UserPreferencesDataStore(androidContext().dataStore) }

    single<HttpClientEngine> { OkHttp.create() }
    singleOf(::ApiClientHolder)

    viewModelOf(::RootViewModel)
    viewModelOf(::MainViewModel)
}
