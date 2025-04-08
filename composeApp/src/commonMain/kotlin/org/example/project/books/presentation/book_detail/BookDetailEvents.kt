package org.example.project.books.presentation.book_detail

import org.example.project.books.domain.Book

sealed interface BookDetailEvents {
    data object onFavoriteClick : BookDetailEvents
    data object onBackClick : BookDetailEvents
    data class onSelectedBookChange(val book: Book) : BookDetailEvents

}