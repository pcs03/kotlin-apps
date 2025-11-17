package nl.pcstet.startupflow.data.auth.repository.model

import nl.pcstet.startupflow.data.auth.datasource.network.model.LoginRequestDto

data class LoginCredentials(
    val username: String,
    val password: String,
)

fun LoginCredentials.toLoginRequestDto(): LoginRequestDto {
    return LoginRequestDto(
        username = username,
        password = password
    )
}