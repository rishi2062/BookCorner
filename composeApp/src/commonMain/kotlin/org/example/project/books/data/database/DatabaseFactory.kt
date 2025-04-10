package org.example.project.books.data.database

import androidx.room.RoomDatabase

expect class DatabaseFactory {

    fun create() : RoomDatabase.Builder<FavoriteBookDB>
}