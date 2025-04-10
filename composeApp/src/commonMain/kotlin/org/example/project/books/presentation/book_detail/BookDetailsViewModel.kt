package org.example.project.books.presentation.book_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.example.project.BookRoutes
import org.example.project.books.domain.Book
import org.example.project.books.domain.BookRepository
import org.example.project.core.domain.Result
import org.example.project.core.domain.asEmptyDataResult

class BookDetailsViewModel(
    private val bookRepository: BookRepository,
    private val savedStateHandle: SavedStateHandle // just to get bookId form navArgunments
) : ViewModel() {

    private val _state = MutableStateFlow(BookDetailState())
    val state = _state
        .onStart {
            getBookDescription()
            observeFavoriteStatus()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _state.value
        )

    private val bookId = savedStateHandle.toRoute<BookRoutes.BookDetails>().bookId
    fun updateSelectedBook(book: Book) {
        _state.update {
            it.copy(
                book = book
            )
        }
    }

    private fun getBookDescription() {
        viewModelScope.launch {
            when (val response = bookId?.let { bookRepository.getBookDescription(it) }) {
                is Result.Error -> {
                    _state.update {
                        it.copy(
                            isLoading = false
                        )
                    }
                }

                is Result.Loading -> {
                    _state.update {
                        it.copy(isLoading = true)
                    }
                }

                is Result.Success -> {
                    _state.update {
                        it.copy(
                            book = it.book?.copy(
                                description = response.data
                            ),
                            isLoading = false
                        )
                    }
                }

                else -> {}
            }
        }
    }

    private fun markBookAsFavorite(book: Book) {
        viewModelScope.launch {
           val result = bookRepository.addBookToFavorite(book)

            when(result){
                is Result.Error -> {
                    _state.update {
                        it.copy(
                            isFavorite = false,
                        )
                    }
                }
                else -> {
                    _state.update {
                        it.copy(
                            isFavorite = true,
                        )
                    }
                }
            }
        }
    }

    private fun removeBookFromFavorite() {
        viewModelScope.launch {
            if (bookId != null) {
                bookRepository.removeBookFromFavorite(bookId)
            }

        }
    }

    private fun observeFavoriteStatus() {
        if (bookId != null) {
            bookRepository.isBookFavorite(bookId)
                .onEach { isFavorite ->
                    _state.update {
                        it.copy(
                            isFavorite = isFavorite
                        )
                    }
                }
                .launchIn(viewModelScope)
        }
    }

}