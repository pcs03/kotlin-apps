package com.example.marsphotos.mock

import com.example.marsphotos.model.MarsPhoto

object MockDataSource {
    val photosList = listOf(
        MarsPhoto(
            id = "img1",
            imageSource = "url.1"
        ),
        MarsPhoto(
            id = "img2",
            imageSource = "url.2"
        ),
    )
}