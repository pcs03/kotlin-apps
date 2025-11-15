package nl.pcstet.navigation.home.domain

import kotlinx.coroutines.flow.Flow
import nl.pcstet.navigation.core.data.utils.DataState
import nl.pcstet.navigation.home.domain.model.User

interface UserRepository {
    fun getUser(): Flow<DataState<User>>
}
