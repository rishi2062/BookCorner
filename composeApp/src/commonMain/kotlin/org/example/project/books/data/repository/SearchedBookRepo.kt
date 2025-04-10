package org.example.project.books.data.repository

import androidx.sqlite.SQLiteException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.example.project.books.data.database.FavoriteBookDao
import org.example.project.books.data.network.RemoteBookDataSource
import org.example.project.books.data.mappers.toBook
import org.example.project.books.data.mappers.toBookEntity
import org.example.project.books.domain.Book
import org.example.project.books.domain.BookRepository
import org.example.project.core.domain.DataError
import org.example.project.core.domain.Result
import org.example.project.core.domain.map

class SearchedBookRepo(
    private val remoteBookDataSource: RemoteBookDataSource,
    private val favoriteBookDao: FavoriteBookDao
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

    override suspend fun getBookDescription(bookId: String): Result<String?, DataError.Remote> {
        val localResult = favoriteBookDao.getFavoriteBook(bookId)

        if(localResult == null){
            return remoteBookDataSource.getBookDescription(bookId).map { it.description }
        }
        else{
            return Result.Success(localResult.description)
        }

    }

    override fun getFavoriteBooks(): Flow<List<Book>> {
        return favoriteBookDao.getFavoriteBooks().map { it.map { it.toBook() } }
    }

    override suspend fun addBookToFavorite(book: Book): Result<Unit, DataError.Local> {
        return try{
            favoriteBookDao.upsertBook(book.toBookEntity())
            Result.Success(Unit)
        }catch (e : SQLiteException){
            Result.Error(DataError.Local.DISK_FULL)
        }
    }

    override fun isBookFavorite(bookId: String): Flow<Boolean> {
        return favoriteBookDao.getFavoriteBooks().map {
            it.any { it.id == bookId }
        }
    }

    override suspend fun removeBookFromFavorite(bookId: String) {
        favoriteBookDao.deleteFavoriteBook(bookId)
    }
}