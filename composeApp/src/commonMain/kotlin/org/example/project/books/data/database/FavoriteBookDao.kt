package org.example.project.books.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteBookDao {
    @Upsert
    suspend fun upsertBook(book : BookEntity)

    @Query("Select * from favorite_books")
    fun getFavoriteBooks(): Flow<List<BookEntity>>

    @Query("Select * from favorite_books where id = :id")
    suspend fun getFavoriteBook(id: String): BookEntity?

    @Query("Delete from favorite_books where id = :id")
    suspend fun deleteFavoriteBook(id: String)
}