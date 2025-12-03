package nl.pcstet.core.network.model

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponseDto(
    val accessToken: String
)