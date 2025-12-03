package nl.pcstet.core.network.di

import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import nl.pcstet.core.network.AuthNetworkDataSource
import nl.pcstet.core.network.KtorAuthNetworkDataSource
import nl.pcstet.core.network.utils.HttpClientFactory
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val NetworkModule = module {
    single<HttpClientEngine> { OkHttp.create() }
    single<HttpClient> { HttpClientFactory.create(get()) }
    singleOf(::KtorAuthNetworkDataSource) { bind<AuthNetworkDataSource>() }
}