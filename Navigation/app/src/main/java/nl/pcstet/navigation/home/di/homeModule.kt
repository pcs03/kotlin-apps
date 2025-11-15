package nl.pcstet.navigation.home.di

import nl.pcstet.navigation.home.data.network.DummyJsonUserApiService
import nl.pcstet.navigation.home.data.network.UserApiService
import nl.pcstet.navigation.home.data.repository.NetworkUserRepository
import nl.pcstet.navigation.home.domain.UserRepository
import nl.pcstet.navigation.home.domain.model.User
import nl.pcstet.navigation.home.presentation.HomeViewModel
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val homeModule = module {
    singleOf(::DummyJsonUserApiService) {bind<UserApiService>()}
    singleOf(::NetworkUserRepository) {bind<UserRepository>()}
    viewModelOf(::HomeViewModel)
}