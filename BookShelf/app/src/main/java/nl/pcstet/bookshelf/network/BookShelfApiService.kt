package nl.pcstet.bookshelf.network

import nl.pcstet.bookshelf.model.VolumesResponse
import retrofit2.http.GET


interface BookShelfApiService {
    @GET("volumes?q=jazz+history&maxResults=20")
    suspend fun getVolumes(): VolumesResponse
}
