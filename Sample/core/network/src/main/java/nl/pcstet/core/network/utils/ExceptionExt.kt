package nl.pcstet.core.network.utils

import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.serialization.SerializationException
import nl.pcstet.core.Error

fun Exception.toNetworkError(): Error.Data.Network {
    return when(this) {
        is UnresolvedAddressException -> Error.Data.Network.NO_INTERNET
        is SerializationException -> Error.Data.Network.SERIALIZATION
        is ClientRequestException -> {
            when(this.response.status.value) {
                400 -> Error.Data.Network.BAD_REQUEST
                401 -> Error.Data.Network.UNAUTHORIZED
                404 -> Error.Data.Network.NOT_FOUND
                408 -> Error.Data.Network.REQUEST_TIMEOUT
                413 -> Error.Data.Network.PAYLOAD_TOO_LARGE
                429 -> Error.Data.Network.TOO_MANY_REQUESTS
                else -> Error.Data.Network.GENERIC_CLIENT
            }
        }
        is ServerResponseException -> {
            when(this.response.status.value) {
                500 -> Error.Data.Network.SERVER_ERROR
                502 -> Error.Data.Network.BAD_GATEWAY
                else -> Error.Data.Network.GENERIC_SERVER
            }
        }
        else -> Error.Data.Network.UNKNOWN
    }
}