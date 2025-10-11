package nl.pcstet.bookshelf

import android.app.Application
import nl.pcstet.bookshelf.data.AppContainer
import nl.pcstet.bookshelf.data.DefaultAppContainer

class BookShelfApplication: Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}