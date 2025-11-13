package nl.pcstet.navigation

import android.app.Application
import nl.pcstet.navigation.auth.di.authModule
import nl.pcstet.navigation.home.di.homeModule
import nl.pcstet.navigation.main.di.mainAppModule
import nl.pcstet.navigation.onboarding.di.onboardingModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class NavigationApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@NavigationApp)
            modules(
                mainAppModule,
                onboardingModule,
                authModule,
                homeModule,
            )
        }
    }
}