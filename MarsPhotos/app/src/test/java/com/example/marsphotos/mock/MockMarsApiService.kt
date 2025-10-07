package com.example.marsphotos.mock

import com.example.marsphotos.model.MarsPhoto
import com.example.marsphotos.network.MarsApiService

class MockMarsApiService : MarsApiService {
    override suspend fun getPhotos(): List<MarsPhoto> {
        return MockDataSource.photosList
    }
}