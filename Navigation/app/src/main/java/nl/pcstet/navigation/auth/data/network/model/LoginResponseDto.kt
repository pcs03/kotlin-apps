package nl.pcstet.navigation.auth.data.network.model

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponseDto(
    val accessToken: String
)
