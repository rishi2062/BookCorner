package org.example.project.books.presentation.book_list

import org.example.project.books.domain.Book
import org.example.project.core.presentation.UiText

data class BookListState(
    val isLoading: Boolean = false,
    val searchQuery: String = "Kotlin",
    val books: List<Book> = emptyList(),
    val favorites: List<Book> = emptyList(),
    val error: UiText? = null,
    val selectedTabIndex: Int = 0,

    ) {

}
