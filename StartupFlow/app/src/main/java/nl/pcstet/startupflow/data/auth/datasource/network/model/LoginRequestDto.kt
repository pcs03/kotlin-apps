package nl.pcstet.startupflow.data.auth.datasource.network.model

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequestDto(
    val username: String,
    val password: String,
)