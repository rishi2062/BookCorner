package org.example.project.books.domain

import org.example.project.core.domain.DataError
import org.example.project.core.domain.Result

interface BookRepository {
    suspend fun searchBooks(query: String, resultLimit: Int? = null, language: String? = "eng"): Result<List<Book>, DataError.Remote>
}