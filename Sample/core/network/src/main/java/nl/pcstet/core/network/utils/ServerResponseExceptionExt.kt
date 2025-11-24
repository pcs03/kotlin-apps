package nl.pcstet.core.network.utils

import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.serialization.SerializationException
import nl.pcstet.core.Error

fun ServerResponseException.toNetworkError(): Error.Data.Network {
    return when (this.response.status.value) {
        500 -> Error.Data.Network.SERVER_ERROR
        502 -> Error.Data.Network.BAD_GATEWAY
        else -> Error.Data.Network.GENERIC_SERVER
    }
}