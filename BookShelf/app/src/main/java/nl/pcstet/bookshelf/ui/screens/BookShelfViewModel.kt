package nl.pcstet.bookshelf.ui.screens

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.launch
import nl.pcstet.bookshelf.BookShelfApplication
import nl.pcstet.bookshelf.data.BookShelfRepository
import nl.pcstet.bookshelf.model.Book
import nl.pcstet.bookshelf.model.VolumesResponse
import okio.IOException

sealed interface BookShelfUiState {
    data class Success(val books: List<Book>) : BookShelfUiState
    object Loading : BookShelfUiState
    object Error : BookShelfUiState
}

class BookShelfViewModel(private val bookShelfRepository: BookShelfRepository) : ViewModel() {
    var bookShelfUiState: BookShelfUiState by mutableStateOf(BookShelfUiState.Loading)
        private set

    init {
        getVolumes()
    }

    fun printVolumes() {
        Log.d("VIEWMODEL", bookShelfUiState.toString())
    }

    fun getVolumes() {
        viewModelScope.launch {
            bookShelfUiState = try {
                BookShelfUiState.Success(bookShelfRepository.getBooks())
            } catch( e: IOException) {
                BookShelfUiState.Error
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = this[APPLICATION_KEY] as BookShelfApplication
                val bookShelfRepository = application.container.bookShelfRepository
                BookShelfViewModel(bookShelfRepository = bookShelfRepository)
            }
        }
    }
}
