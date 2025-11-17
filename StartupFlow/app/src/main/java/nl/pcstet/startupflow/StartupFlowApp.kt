package nl.pcstet.startupflow

import android.app.Application
import nl.pcstet.startupflow.data.auth.datasource.di.authDataSourceModule
import nl.pcstet.startupflow.data.auth.repository.di.authRepositoryModule
import nl.pcstet.startupflow.data.core.datasource.di.coreDataSourceModule
import nl.pcstet.startupflow.ui.auth.di.authUiModule
import nl.pcstet.startupflow.ui.core.di.coreUiModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class StartupFlowApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@StartupFlowApp)
            modules(
                coreDataSourceModule,
                coreUiModule,

                authDataSourceModule,
                authRepositoryModule,
                authUiModule,
            )
        }
    }
}