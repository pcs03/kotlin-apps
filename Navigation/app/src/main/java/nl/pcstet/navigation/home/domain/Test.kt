package nl.pcstet.navigation.home.domain

import nl.pcstet.navigation.core.data.utils.ApiResult

interface TestRepo {
    fun outTest(): OutResult<Any>
    fun normalTest(): ApiResult<String>

    fun listTest(): List<ApiResult<String>>
}


class Repo() : TestRepo {
    override fun outTest(): OutResult<String> {
        val result = OutResult.Success("Hello!")
        return result
    }

    override fun normalTest(): ApiResult<String> {
        val result = ApiResult.Success("Hello")
        return result
    }

    override fun listTest(): List<ApiResult<String>> {
        val result = (1..10).toList().map { ApiResult.Success(it.toString()) }
        return result
    }
}

sealed interface OutResultInterface<out R> {}

sealed class OutResult<out R> {
    data class Success<out T>(val data: T) : OutResult<T>()
    data class Failure(val exception: Exception) : OutResult<Nothing>()
}

