package nl.pcstet.startupflow.data.core.manager.di

import nl.pcstet.startupflow.data.core.manager.dispatcher.DispatcherManager
import nl.pcstet.startupflow.data.core.manager.dispatcher.DispatcherManagerImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val coreManagerModule = module {
    singleOf(::DispatcherManagerImpl) { bind<DispatcherManager>() }
}