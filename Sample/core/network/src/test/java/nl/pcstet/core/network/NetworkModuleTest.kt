package nl.pcstet.core.network

import nl.pcstet.core.network.di.NetworkModule
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.test.KoinTest
import org.koin.test.verify.verify
import kotlin.test.Test

class NetworkModuleTest : KoinTest {
    @OptIn(KoinExperimentalAPI::class)
    @Test
    fun checkNetworkModule() {
        NetworkModule.verify()
    }
}