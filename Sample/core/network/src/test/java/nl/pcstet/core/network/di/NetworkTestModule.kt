package nl.pcstet.core.network.di

import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.mock.MockEngine
import nl.pcstet.core.network.utils.HttpClientFactory
import org.koin.dsl.module

internal val networkTestModule = module {
    single<HttpClient> { HttpClientFactory.create(get()) }
}