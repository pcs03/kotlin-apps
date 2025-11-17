package nl.pcstet.startupflow.ui.core.di

import nl.pcstet.startupflow.ui.core.feature.appnav.AppNavViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val coreUiModule = module {
    viewModelOf(::AppNavViewModel)
}