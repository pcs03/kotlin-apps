package nl.pcstet.startupflow.data.auth.datasource.network.model

import kotlinx.serialization.Serializable

@Serializable
data class TestResponseDto(
    val status: String
)