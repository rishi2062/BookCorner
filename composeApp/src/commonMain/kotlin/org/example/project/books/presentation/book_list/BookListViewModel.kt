package org.example.project.books.presentation.book_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.example.project.books.domain.Book
import org.example.project.books.domain.BookRepository
import org.example.project.core.domain.Result
import org.example.project.core.presentation.toUIText

class BookListViewModel(
    private val bookRepository: BookRepository
) : ViewModel() {

    private val _state = MutableStateFlow(BookListState())

    // either, we check initial state here, so that in every recompose , when viewmodel initialises, it should not hit api again
    // or we can put launchedEffect and check if cachedBook is empty then only make api call.
    val state = _state
        .onStart {
            if (cachedBooks.isNotEmpty()) {
                _state.update {
                    it.copy(books = cachedBooks)
                }
            } else {
                observeSearchQuery()
            }
        }
        // if we don't give stateIn , it becomes coldFlow,
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _state.value
        )

    private var searchJob: Job? = null
    private var cachedBooks = emptyList<Book>()

    val books = (1..100).map {
        Book(
            id = it.toString(),
//            imageUrl = "https://picsum.photos/200/300?random=$it",
            imageUrl = "https://test.com",
            title = "Book $it",
            authors = listOf(Book.Author(name = "Author $it")),
            averageRating = 4.7666
        )
    }

//    init {
//        _state.update {
//            it.copy(books = books)
//        }
//    }

    fun observeSearchQuery() {
        viewModelScope.launch {
            state
                .map { it.searchQuery }
                .distinctUntilChanged()
                .debounce(500L)
                // for user changed the text, so previous search doesn't matter and hence cancel that job
                .collectLatest { query ->
                    when {
                        query.isBlank() -> {
                            _state.update {
                                it.copy(
                                    books = cachedBooks,
                                    isLoading = false,
                                    error = null
                                )
                            }
                        }

                        query.length >= 2 -> {
                            searchJob?.cancel()
                            searchJob = searchBooks(query)
                        }
                    }
                }
        }
    }

    fun updateSearchQuery(query: String) {
        _state.update {
            it.copy(searchQuery = query)
        }
    }

    private fun searchBooks(query: String) = viewModelScope.launch {
        _state.update {
            it.copy(isLoading = true)
        }
        val response = bookRepository.searchBooks(query)
        when (response) {
            is Result.Error -> {
                _state.update {
                    it.copy(
                        books = emptyList(),
                        isLoading = false,
                        error = response.error?.toUIText()
                    )
                }
            }

            is Result.Success -> {
                _state.update {
                    it.copy(
                        books = response.data,
                        isLoading = false,
                        error = null
                    )
                }
                cachedBooks = response.data
            }

            else -> {}
        }

    }


    fun updateTabSelected(selectedTabIndex: Int) {
        _state.update {
            it.copy(selectedTabIndex = selectedTabIndex)
        }
    }
}
