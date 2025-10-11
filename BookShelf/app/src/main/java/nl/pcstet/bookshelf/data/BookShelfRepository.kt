package nl.pcstet.bookshelf.data

import nl.pcstet.bookshelf.model.Book
import nl.pcstet.bookshelf.model.VolumesResponse
import nl.pcstet.bookshelf.network.BookShelfApiService

interface BookShelfRepository {
    suspend fun getBooks(): List<Book>
}

class NetworkBookShelfRepository(
    private val bookShelfApiService: BookShelfApiService,
) : BookShelfRepository {
    override suspend fun getBooks(): List<Book> {
        return bookShelfApiService.getVolumes().items
    }
}