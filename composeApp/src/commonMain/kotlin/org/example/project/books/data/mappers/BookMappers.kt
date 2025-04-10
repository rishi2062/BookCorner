package org.example.project.books.data.mappers

import org.example.project.books.data.database.BookEntity
import org.example.project.books.data.dto.SearchedBookDTO
import org.example.project.books.domain.Book
import kotlin.uuid.Uuid

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

fun BookEntity.toBook(): Book {
    return Book(
        id = id,
        imageUrl = imageUrl,
        title = title,
        authors = authors?.map { it.toAuthor() ?: Book.Author() },
        description = description,
        languages = languages,
        firstPublishYear = firstPublishYear,
        averageRating = averageRating,
        ratingCount = ratingCount,
        numPages = numPages,
        numEditions = numEditions
    )
}

fun Book.toBookEntity(): BookEntity {
    return BookEntity(
        id = id,
        imageUrl = imageUrl,
        title = title,
        authors = authors?.map {it.name ?: ""},
        description = description,
        languages = languages,
        firstPublishYear = firstPublishYear,
        averageRating = averageRating,
        ratingCount = ratingCount,
        numPages = numPages,
        numEditions = numEditions
    )
}
