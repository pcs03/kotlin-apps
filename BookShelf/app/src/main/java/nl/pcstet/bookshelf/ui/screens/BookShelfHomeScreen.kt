package nl.pcstet.bookshelf.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import nl.pcstet.bookshelf.model.Book

@Composable
fun BookShelfHomeScreen(
    modifier: Modifier = Modifier,
    bookShelfViewModel: BookShelfViewModel = viewModel(factory = BookShelfViewModel.Factory)
) {
    val bookShelfUiState = bookShelfViewModel.bookShelfUiState

    when (bookShelfUiState) {
        is BookShelfUiState.Success -> BookGrid(books = bookShelfUiState.books, modifier = modifier)
        is BookShelfUiState.Loading -> Text(text = "Loading")
        is BookShelfUiState.Error -> Text(text = "Error")
    }

    bookShelfViewModel.printVolumes()
}

@Composable
private fun BookGrid(
    books: List<Book>,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 150.dp),
        modifier = modifier.fillMaxSize()
    ) {
        items(items = books, key = { book -> book.id }) { book ->
            BookCard(
                book = book,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Composable
private fun BookCard(
    book: Book,
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier) {
        BookImage(book = book, modifier = Modifier.fillMaxSize())
    }
}

@Composable
private fun BookImage(
    book: Book,
    modifier: Modifier = Modifier
) {
    AsyncImage(
        model = ImageRequest.Builder(context = LocalContext.current)
            .data(book.info.imageLinks?.thumbnailLink)
            .crossfade(true)
            .build(),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = modifier
    )
}












