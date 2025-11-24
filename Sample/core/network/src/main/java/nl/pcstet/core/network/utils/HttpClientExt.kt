package nl.pcstet.core.network.utils

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.request
import kotlinx.serialization.SerializationException
import nl.pcstet.core.Error
import nl.pcstet.core.Result
import java.io.IOException


suspend inline fun <reified T> HttpClient.safeRequest(urlString: String, block: HttpRequestBuilder.() -> Unit): Result<T, Error.Data.Network> {
    return try {
        val response = this.request(urlString) { block() }
        Result.Success(response.body<T>())
    } catch (e: ClientRequestException) {
        Result.Error<Error.Data.Network>(error = e.toNetworkError())
    } catch (e: ServerResponseException) {
        Result.Error<Error.Data.Network>(error = e.toNetworkError())
    } catch (e: IOException) {
        Result.Error<Error.Data.Network>(Error.Data.Network.NO_INTERNET)
    } catch (e: SerializationException) {
        Result.Error<Error.Data.Network>(Error.Data.Network.SERIALIZATION)
    }
}