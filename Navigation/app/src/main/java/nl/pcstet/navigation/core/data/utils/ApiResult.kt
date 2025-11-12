package nl.pcstet.navigation.core.data.utils

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.request
import io.ktor.client.statement.HttpResponse
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException


// Shamelessly copied from: https://github.com/shahzadansari/RickAndMorty/blob/main/core/src/main/java/com/example/core/ApiResponse.kt

suspend inline fun <reified T> HttpClient.safeRequest(block: HttpRequestBuilder.() -> Unit): ApiResult<T> =
    try {
        val response = request { block() }
        ApiResult.Success(response.body())
    } catch (exception: ClientRequestException) {
        ApiResult.Failure(exception.toApiException())
    } catch (exception: ServerResponseException) {
        ApiResult.Failure(exception.toApiException())
    } catch (exception: Exception) {
        ApiResult.Failure(exception.toApiException())
    } catch (throwable: Throwable) {
        ApiResult.Failure(throwable.toApiException())
    }

sealed class ApiResult<T>(open val data: T? = null, open val cause: ApiException? = null) {
    class Success<T>(override val data: T) : ApiResult<T>(data)
    class Failure<T>(override val cause: ApiException) : ApiResult<T>(cause = cause)
}

sealed class ApiException(open val statusCode: Int = -1, open val errorMsg: String) : Exception(errorMsg) {
    data class Generic(override val errorMsg: String = "Something went wrong. Please try again!") : ApiException(errorMsg = errorMsg)
    data class HttpError(override val statusCode: Int, override val errorMsg: String = "Request could not be processed! Code :$statusCode") : ApiException(errorMsg = errorMsg)
    data object Network : ApiException(errorMsg = "Failed to connect. Please try again!")
}

fun Exception.toApiException(): ApiException {
    return when (this) {
        is ApiException -> this
        is UnknownHostException, is ConnectException, is SocketTimeoutException -> ApiException.Network
        is ClientRequestException -> ApiException.HttpError(
            statusCode = this.response.status.value,
            errorMsg = this.message
        )

        is ServerResponseException -> ApiException.HttpError(
            statusCode = this.response.status.value,
            errorMsg = this.message
        )

        is IOException -> ApiException.Network
        is HttpExceptions -> ApiException.HttpError(
            statusCode = this.response.status.value,
            errorMsg = this.message
        )

        else -> ApiException.Generic(this.message ?: "Unknown error")
    }
}

fun Throwable.toApiException(): ApiException {
    return ApiException.Generic(this.message ?: "Unknown error")
}

class HttpExceptions(
    response: HttpResponse,
    failureReason: String?,
    cachedResponseText: String
) : ResponseException(response, cachedResponseText) {
    override val message: String = "Status: ${response.status}" + " Failure: $failureReason"
}
