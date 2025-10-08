package nl.pcstet.amphibians.data

import nl.pcstet.amphibians.model.Amphibian
import nl.pcstet.amphibians.network.AmphibiansApiService

interface AmphibiansRepository {
    suspend fun getAmphibians(): List<Amphibian>
}

class NetworkAmphibiansRepository(
    private val amphibianApiService: AmphibiansApiService
) : AmphibiansRepository {
    override suspend fun getAmphibians(): List<Amphibian> {
        return amphibianApiService.getAmphibians()
    }
}

class LocalAmphibiansRepository(
    private val amphibiansDataSource: LocalAmphibiansDataSource
) : AmphibiansRepository {
    override suspend fun getAmphibians(): List<Amphibian> {
        return amphibiansDataSource.getAmphibians()
    }
}