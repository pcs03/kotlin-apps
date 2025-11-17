package nl.pcstet.startupflow.data.core.datasource.network.utils

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.request

suspend inline fun <reified T> HttpClient.safeRequest(block: HttpRequestBuilder.() -> Unit): ApiResult<T> {
    return try {
        val response = this.request { block() }
        ApiResult.Success(response.body<T>())
    } catch (exception: Exception) {
        ApiResult.Failure(exception.toApiException())
    } catch (throwable: Throwable) {
        ApiResult.Failure(throwable.toApiException())
    }
}

suspend inline fun <reified T> HttpClient.safeRequest(urlString: String, block: HttpRequestBuilder.() -> Unit): ApiResult<T> {
    return try {
        val response = this.request(urlString) { block() }
        ApiResult.Success(response.body<T>())
    } catch (exception: Exception) {
        ApiResult.Failure(exception.toApiException())
    } catch (throwable: Throwable) {
        ApiResult.Failure(throwable.toApiException())
    }
}
