package nl.pcstet.navigation.core.data.utils

// Shamelessly copied from: https://github.com/shahzadansari/RickAndMorty/blob/main/core/src/main/java/com/example/core/DataState.kt

sealed class DataState<T>(open val data: T? = null, open val cause: ApiException? = null) {
    data class Success<T>(override val data: T) : DataState<T>(data = data)
    data class Error<T>(override val cause: ApiException) : DataState<T>(cause = cause)
    data class Loading<T>(val isLoading: Boolean = true) : DataState<T>()
}


/** Inspired by: https://getstream.io/blog/modeling-retrofit-responses/ */
//fun <T : Any> DataState<T>.onLoading(onLoading: (Boolean) -> Unit): DataState<T> = apply {
//    onLoading(this is DataState.Loading)
//}
//
//fun <T : Any> DataState<T>.onSuccess(onSuccess: (T) -> Unit): DataState<T> = apply {
//    if (this is DataState.Success) {
//        onSuccess(data)
//    }
//}
//
//fun <T : Any> DataState<T>.onError(onError: (ApiException) -> Unit): DataState<T> = apply {
//    if (this is DataState.Error) {
//        onError(cause)
//    }
//}
