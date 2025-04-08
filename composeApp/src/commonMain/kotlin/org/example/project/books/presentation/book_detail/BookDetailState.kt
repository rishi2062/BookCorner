package org.example.project.books.presentation.book_detail

import org.example.project.books.domain.Book

data class BookDetailState(
    val isLoading : Boolean = true,
    val isFavorite : Boolean = false,
    val book : Book? = null,
)