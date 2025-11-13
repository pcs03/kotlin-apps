package nl.pcstet.navigation.auth.di

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import nl.pcstet.navigation.auth.data.network.ApiService
import nl.pcstet.navigation.auth.data.network.DummyJsonApiService
import nl.pcstet.navigation.auth.data.repository.AuthRepository
import nl.pcstet.navigation.auth.data.repository.AuthRepositoryImpl
import nl.pcstet.navigation.auth.presentation.login.LoginViewModel
import nl.pcstet.navigation.core.data.network.ApiClientHolder
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

//fun provideHttpClient(engine: )


val authModule = module {
    single<HttpClientEngine> { OkHttp.create() }
//    single<HttpClient>(named("auth")) { provideHttpClient(get(), "https://dummyjson.com/") }
    singleOf(::ApiClientHolder)

//    single<ApiService> { DummyJsonApiService(get(named("auth"))) }
    single<ApiService> { DummyJsonApiService(get()) }


    single<CoroutineScope> { CoroutineScope(SupervisorJob() + Dispatchers.IO) }

    singleOf(::AuthRepositoryImpl) { bind<AuthRepository>() }

    viewModelOf(::LoginViewModel)
}