package org.example.project.books.data.network

import org.example.project.books.data.dto.BookWorkDTO
import org.example.project.books.data.dto.SearchBookResponseDTO
import org.example.project.core.domain.DataError
import org.example.project.core.domain.Result

interface RemoteBookDataSource {
    suspend fun searchBooks(
        query: String,
        resultLimit: Int? = null,
        language: String? = "eng"
    ): Result<SearchBookResponseDTO, DataError.Remote>

    suspend fun getBookDescription(
        bookId: String,
    ): Result<BookWorkDTO, DataError.Remote>
}