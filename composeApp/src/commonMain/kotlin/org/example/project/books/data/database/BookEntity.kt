package org.example.project.books.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import org.example.project.books.domain.Book.Author

@Entity(tableName = "favorite_books")

data class BookEntity(
    @PrimaryKey(autoGenerate = false) val id : String,
    val imageUrl : String? = null,
    val title : String? = null,
    val authors : List<String>? = emptyList(),
    val description : String? = null,
    val languages : List<String>? = emptyList(),
    val firstPublishYear : String? = null,
    val averageRating : Double? = null,
    val ratingCount : String? = null,
    val numPages : Int? = null,
    val numEditions: Int? = null,
)
