package org.example.project.books.data.database

import androidx.room.RoomDatabaseConstructor


//For KMP we require platform specific implementation for local dbs , but room automatically handles this via these steps and constructor class
@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object BookDatabaseConstructor : RoomDatabaseConstructor<FavoriteBookDB>{
    override fun initialize(): FavoriteBookDB
}