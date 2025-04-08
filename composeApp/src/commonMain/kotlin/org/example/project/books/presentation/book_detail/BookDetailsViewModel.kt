package org.example.project.books.presentation.book_detail

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.example.project.books.domain.Book

class BookDetailsViewModel : ViewModel() {

    private val _state = MutableStateFlow(BookDetailState())
    val state = _state.asStateFlow()

    fun updateSelectedBook(book: Book) {
        _state.update {
            it.copy(
                book = book
            )
        }
    }
}