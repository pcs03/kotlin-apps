package nl.pcstet.startupflow.data.utils

import nl.pcstet.startupflow.data.core.datasource.network.utils.ApiException

sealed interface DataState<out T> {
    data class Success<out T>(val data: T): DataState<T>
    data class Error<T>(val error: ApiException) : DataState<T>
    data object Loading : DataState<Nothing>
}

inline fun <T, R> DataState<T>.map(map: (T) -> R): DataState<R> {
    return when (this) {
        is DataState.Error -> DataState.Error(this.error)
        is DataState.Success -> DataState.Success(map(this.data))
        is DataState.Loading -> this
    }
}

inline fun <T> DataState<T>.onSuccess(action: (T) -> Unit): DataState<T> {
    return when(this) {
        is DataState.Error -> this
        is DataState.Success -> {
            action(this.data)
            this
        }
        is DataState.Loading -> this
    }
}

inline fun <T> DataState<T>.onFailure(action: (ApiException) -> Unit): DataState<T> {
    return when(this) {
        is DataState.Error -> {
            action(this.error)
            this
        }
        is DataState.Success -> this
        is DataState.Loading -> this
    }
}
inline fun <T> DataState<T>.onLoading(action: () -> Unit): DataState<T> {
    return when(this) {
        is DataState.Error -> this
        is DataState.Success -> this
        is DataState.Loading -> {
            action()
            this
        }
    }
}
