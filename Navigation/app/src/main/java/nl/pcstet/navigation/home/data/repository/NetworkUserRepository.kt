package nl.pcstet.navigation.home.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import nl.pcstet.navigation.core.data.utils.ApiResult
import nl.pcstet.navigation.core.data.utils.DataState
import nl.pcstet.navigation.home.data.network.DummyJsonUserApiService
import nl.pcstet.navigation.home.data.network.model.asExternalModel
import nl.pcstet.navigation.home.domain.UserRepository
import nl.pcstet.navigation.home.domain.model.User

class NetworkUserRepository(
    private val userApiService: DummyJsonUserApiService
) : UserRepository {
    override fun getUser(): Flow<DataState<User>> = flow {
        emit(DataState.Loading())
        val apiResponse = userApiService.getUser()
        val result = when(apiResponse) {
            is ApiResult.Success -> DataState.Success(apiResponse.data.asExternalModel())
            is ApiResult.Failure -> DataState.Error(apiResponse.cause)
        }

        emit(result)
    }

}