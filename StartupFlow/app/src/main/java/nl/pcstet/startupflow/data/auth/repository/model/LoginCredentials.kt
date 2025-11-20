package nl.pcstet.startupflow.data.auth.repository.model

import nl.pcstet.startupflow.data.auth.datasource.network.model.LoginRequestDto

data class LoginCredentials(
    val email: String,
    val password: String,
)

fun LoginCredentials.toLoginRequestDto(): LoginRequestDto {
    return LoginRequestDto(
        username = email,
        password = password
    )
}