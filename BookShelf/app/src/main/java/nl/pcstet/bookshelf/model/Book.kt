package nl.pcstet.bookshelf.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VolumesResponse(
    val items: List<Book>,
)

@Serializable
data class Book(
    val id: String,
    @SerialName("volumeInfo")
    val info: VolumeInfo
)

@Serializable
data class VolumeInfo(
    val title: String,
    val authors: List<String>? = emptyList(),
    val description: String? = null,
    val pageCount: Int? = null,
    val imageLinks: ImageLinks? = null,
)

@Serializable
data class ImageLinks (
    @SerialName("thumbnail")
    private val thumbnailStringHttp: String
) {
    val thumbnailLink: String = thumbnailStringHttp.replace("http", "https")
}
