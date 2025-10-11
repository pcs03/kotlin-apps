package nl.pcstet.bookshelf.data

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import nl.pcstet.bookshelf.network.BookShelfApiService
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val bookShelfRepository: BookShelfRepository
}

class DefaultAppContainer : AppContainer {
    private val baseUrl = "https://www.googleapis.com/books/v1/"

    private val retrofitJson = Json { ignoreUnknownKeys = true }
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(retrofitJson.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    private val retrofitService: BookShelfApiService by lazy {
        retrofit.create(BookShelfApiService::class.java)
    }

    override val bookShelfRepository: BookShelfRepository by lazy {
        NetworkBookShelfRepository(retrofitService)
    }
}