package nl.pcstet.startupflow.data.auth.datasource.network.model

import kotlinx.serialization.Serializable

@Serializable
data class AuthenticateResponseDto(
    val id: Int,
    val firstName: String,
    val lastName: String
)