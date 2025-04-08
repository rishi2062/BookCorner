package org.example.project.books.data.network

import org.example.project.books.data.dto.SearchBookResponseDTO
import org.example.project.books.data.dto.SearchedBookDTO
import org.example.project.books.domain.Book

fun SearchedBookDTO.toBook(): Book {
    return Book(
        id = id.substringAfterLast("/"),
        title = title,
        languages = languages,
        imageUrl = if (coverKey != null) "https://covers.openlibrary.org/b/olid/${coverKey}-L.jpg" else {
            "https://covers.openlibrary.org/b/olid/${coverAlternativeKey}-L.jpg"
        },
        authors = authorNames?.map { it.toAuthor() },
        firstPublishYear = firstPublishYear.toString(),
        ratingCount = ratingsCount.toString(),
        averageRating = ratingsAverage,
        numPages = numberOfPagesMedian,
        numEditions = numEditions ?: 0
    )

}

fun String.toAuthor() : Book.Author {
    return Book.Author(
        name = this
    )
}
