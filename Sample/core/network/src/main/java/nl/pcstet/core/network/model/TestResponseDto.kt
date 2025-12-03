package nl.pcstet.core.network.model

import kotlinx.serialization.Serializable

@Serializable
data class TestResponseDto(
    val status: String
)