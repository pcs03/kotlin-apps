package nl.pcstet.core.network.model

import kotlinx.serialization.Serializable

@Serializable
data class AuthenticateResponseDto(
    val id: Int,
    val firstName: String
)