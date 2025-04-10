package org.example.project.books.data.network

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import org.example.project.books.data.dto.BookWorkDTO
import org.example.project.books.data.dto.SearchBookResponseDTO
import org.example.project.core.data.request
import org.example.project.core.domain.DataError
import org.example.project.core.domain.Result

private const val BASE_URL = "https://openlibrary.org"

class KtorRemoteBookDataSource(
    private val httpClient: HttpClient,
) : RemoteBookDataSource {
    override suspend fun searchBooks(
        query: String,
        resultLimit: Int?,
        language: String?
    ): Result<SearchBookResponseDTO, DataError.Remote> {
        return request {
            httpClient.get(
                urlString = "$BASE_URL/search.json"
            ) {
                parameter("q", query)
                parameter("limit", resultLimit)
                parameter("language", language)
            }
        }
    }

    override suspend fun getBookDescription(bookId: String): Result<BookWorkDTO, DataError.Remote> {
        return request {
            httpClient.get(
                urlString = "$BASE_URL/works/$bookId.json"
            )
        }
    }
}
