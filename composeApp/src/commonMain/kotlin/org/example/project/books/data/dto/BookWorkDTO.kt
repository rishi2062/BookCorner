package org.example.project.books.data.dto

import kotlinx.serialization.Serializable

@Serializable(with = BookDescriptionSerializer::class)
data class BookWorkDTO(
    val description: String? = null,
)
