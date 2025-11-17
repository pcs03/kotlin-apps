package nl.pcstet.startupflow.data.auth.datasource.network.model

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponseDto(
    val accessToken: String
)