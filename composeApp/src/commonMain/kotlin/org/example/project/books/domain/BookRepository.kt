package org.example.project.books.domain

import kotlinx.coroutines.flow.Flow
import org.example.project.core.domain.DataError
import org.example.project.core.domain.Result

interface BookRepository {
    suspend fun searchBooks(
        query: String,
        resultLimit: Int? = null,
        language: String? = "eng"
    ): Result<List<Book>, DataError.Remote>

    suspend fun getBookDescription(
        bookId: String,
    ): Result<String?, DataError.Remote>

    fun getFavoriteBooks(): Flow<List<Book>>

    suspend fun addBookToFavorite(book: Book): Result<Unit, DataError.Local>

    fun isBookFavorite(bookId: String): Flow<Boolean>

    suspend fun removeBookFromFavorite(bookId: String)


}