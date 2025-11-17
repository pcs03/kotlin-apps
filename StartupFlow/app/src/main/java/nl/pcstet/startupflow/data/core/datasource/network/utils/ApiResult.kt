package nl.pcstet.startupflow.data.core.datasource.network.utils

import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import nl.pcstet.startupflow.data.utils.DataState
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

sealed interface ApiResult<out T> {
    data class Success<out T>(val data: T) : ApiResult<T>
    data class Failure<T>(val error: ApiException) : ApiResult<T>
}

sealed interface ApiException {
    val message: String
    data class Generic(override val message: String = "Something went wrong. Please try again!") :
        ApiException

    data class Http(
        val statusCode: Int,
        override val message: String = "Request could not be processed. HTTP code: $statusCode",
    ) : ApiException

    data class Network(override val message: String = "Failed to connect. Please try again!") : ApiException
}

fun Exception.toApiException(): ApiException {
    return when (this) {
        is ApiException -> this
        is UnknownHostException, is ConnectException, is SocketTimeoutException, is IOException -> ApiException.Network()
        is ClientRequestException -> ApiException.Http(
            statusCode = this.response.status.value,
            message = this.message
        )

        is ServerResponseException -> ApiException.Http(
            statusCode = this.response.status.value,
            message = this.message
        )

        else -> ApiException.Generic()
    }
}

fun Throwable.toApiException(): ApiException {
    return ApiException.Generic()
}

inline fun <T, R> ApiResult<T>.map(map: (T) -> R): ApiResult<R> {
    return when (this) {
        is ApiResult.Failure -> ApiResult.Failure(this.error)
        is ApiResult.Success -> ApiResult.Success(map(this.data))
    }
}

inline fun <T> ApiResult<T>.onSuccess(action: (T) -> Unit): ApiResult<T> {
    return when (this) {
        is ApiResult.Failure -> this
        is ApiResult.Success -> {
            action(this.data)
            this
        }
    }
}

inline fun <T> ApiResult<T>.onFailure(action: (ApiException) -> Unit): ApiResult<T> {
    return when (this) {
        is ApiResult.Failure -> {
            action(this.error)
            this
        }

        is ApiResult.Success -> this
    }
}

fun <T> ApiResult<T>.toDataState(): DataState<T> {
    return when (this) {
        is ApiResult.Failure -> DataState.Error(this.error)
        is ApiResult.Success -> DataState.Success(this.data)
    }
}

