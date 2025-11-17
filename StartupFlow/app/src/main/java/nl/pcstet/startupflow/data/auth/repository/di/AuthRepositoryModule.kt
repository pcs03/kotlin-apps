package nl.pcstet.startupflow.data.auth.repository.di

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import nl.pcstet.startupflow.data.auth.repository.AuthRepository
import nl.pcstet.startupflow.data.auth.repository.AuthRepositoryImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val authRepositoryModule = module {
    single { CoroutineScope(SupervisorJob() + Dispatchers.Default) }
    singleOf(::AuthRepositoryImpl) { bind<AuthRepository>() }
}
