package com.example.marsphotos.mock

import com.example.marsphotos.rules.TestDispatcherRule
import com.example.marsphotos.ui.screens.MarsUiState
import com.example.marsphotos.ui.screens.MarsViewModel
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals

class MarsViewModelTest {
    @get:Rule
    val testDispatcher = TestDispatcherRule()

    @Test
    fun marsViewModel_getMarsPhotos_verifyMarsUiStateSuccess() = runTest {
        val marsViewModel = MarsViewModel(
            marsPhotosRepository = MockNetworkMarsPhotosRepository()
        )

        assertEquals(
            MarsUiState.Success("Success: ${MockDataSource.photosList.size} Mars photos retrieved"),
            marsViewModel.marsUiState
        )
    }
}