package org.example.project.books.presentation.book_list

import org.example.project.books.domain.Book

sealed interface BookListEvents {
    data class onSearchQueryChange(val query: String) : BookListEvents
    data class onBookClick(val book: Book) : BookListEvents
    data class onTabSelected(val index: Int) : BookListEvents
}