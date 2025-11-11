package nl.pcstet.navigation

import android.app.Application
import nl.pcstet.navigation.auth.di.authModule
import nl.pcstet.navigation.core.di.userPreferencesModule
import nl.pcstet.navigation.home.di.homeModule
import nl.pcstet.navigation.navigation.di.navigationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class NavigationApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@NavigationApp)
            modules(
                userPreferencesModule,
                authModule,
                homeModule,
                navigationModule
            )
        }
    }
}