package nl.pcstet.startupflow.data.auth.datasource.di

import nl.pcstet.startupflow.data.auth.datasource.network.AuthApiService
import nl.pcstet.startupflow.data.auth.datasource.network.DummyJsonAuthApiService
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val authDataSourceModule = module {
    singleOf(::DummyJsonAuthApiService) { bind<AuthApiService>() }
}
