package nl.pcstet.core.network.utils

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.request
import nl.pcstet.core.Error
import nl.pcstet.core.Result


suspend inline fun <reified T> HttpClient.safeRequest(urlString: String, block: HttpRequestBuilder.() -> Unit): Result<T, Error.Data.Network> {
    return try {
        val response = this.request(urlString) { block() }
        Result.Success(response.body<T>())
    } catch (exception: Exception) {
        Result.Error(exception.toNetworkError())
    }
}