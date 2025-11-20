package nl.pcstet.startupflow.ui.main.di

import nl.pcstet.startupflow.ui.main.feature.home.HomeViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val mainUiModule = module {
    viewModelOf(::HomeViewModel)
}