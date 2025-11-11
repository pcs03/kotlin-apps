package nl.pcstet.navigation.navigation.di

import nl.pcstet.navigation.navigation.presentation.RootViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val navigationModule = module {
    viewModelOf(::RootViewModel)
}