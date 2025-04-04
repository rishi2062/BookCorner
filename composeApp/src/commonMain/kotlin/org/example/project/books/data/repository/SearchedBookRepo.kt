package org.example.project.books.data.repository

import org.example.project.books.data.network.RemoteBookDataSource
import org.example.project.books.data.network.toBook
import org.example.project.books.domain.Book
import org.example.project.books.domain.BookRepository
import org.example.project.core.domain.DataError
import org.example.project.core.domain.Result
import org.example.project.core.domain.map

class SearchedBookRepo(
    private val remoteBookDataSource: RemoteBookDataSource
) : BookRepository {
    override suspend fun searchBooks(
        query: String,
        resultLimit: Int?,
        language: String?
    ): Result<List<Book>, DataError.Remote> {
        return remoteBookDataSource.searchBooks(query, resultLimit, language)
            .map { result ->
                result.results.map { it.toBook() }
            }
    }
}