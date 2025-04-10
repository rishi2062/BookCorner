package org.example.project.books.data.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [BookEntity::class],
    version = 1,
)
@TypeConverters(
    StringListTypeConverters::class
)
@ConstructedBy(BookDatabaseConstructor::class)
abstract class FavoriteBookDB : RoomDatabase(){
    abstract val favoriteBookDao: FavoriteBookDao

    companion object {
        const val DB_NAME = "book.db"
    }

}