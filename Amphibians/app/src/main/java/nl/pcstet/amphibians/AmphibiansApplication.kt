package nl.pcstet.amphibians

import android.app.Application
import nl.pcstet.amphibians.data.AppContainer
import nl.pcstet.amphibians.data.DefaultAppContainer

class AmphibiansApplication : Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}