package org.example.project.books.domain

data class Book(
    val id : String,
    val imageUrl : String? = null,
    val title : String? = null,
    val authors : List<Author>? = emptyList(),
    val description : String? = null,
    val languages : List<String>? = emptyList(),
    val firstPublishYear : String? = null,
    val averageRating : Double? = null,
    val ratingCount : String? = null,
    val numPages : Int? = null,
    val numEditions: Int? = null,
){
    data class Author(
        val name : String? = null,
    )
}
