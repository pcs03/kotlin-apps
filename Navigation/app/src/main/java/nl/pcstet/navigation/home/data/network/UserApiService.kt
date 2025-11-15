package nl.pcstet.navigation.home.data.network

import nl.pcstet.navigation.core.data.utils.ApiResult
import nl.pcstet.navigation.home.data.network.model.UserDto

interface UserApiService {
    suspend fun getUser(): ApiResult<UserDto>
}