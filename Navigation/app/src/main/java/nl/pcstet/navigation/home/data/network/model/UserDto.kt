package nl.pcstet.navigation.home.data.network.model

import kotlinx.serialization.Serializable
import nl.pcstet.navigation.home.domain.model.User

@Serializable
data class UserDto (
    val id: Int,
    val firstName: String,
    val lastName: String,
)

fun UserDto.asExternalModel(): User =
    User(
        id = id,
        firstName = firstName,
        lastName = lastName
    )