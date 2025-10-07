package com.example.marsphotos.mock

import com.example.marsphotos.data.NetworkMarsPhotosRepository
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.DefaultAsserter.assertEquals
import kotlin.test.assertEquals

class NetworkMarsRepositoryTest {
    @Test
    fun networkMarsPhotosRepository_getMarsPhotos_verifyPhotoList() = runTest {
        val repository = NetworkMarsPhotosRepository(
            marsApiService = MockMarsApiService()
        )

        assertEquals(MockDataSource.photosList, repository.getMarsPhotos())
    }
}