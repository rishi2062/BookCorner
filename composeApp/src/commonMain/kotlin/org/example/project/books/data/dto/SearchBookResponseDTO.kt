package org.example.project.books.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchBookResponseDTO(
    @SerialName("docs")
    val results : List<SearchedBookDTO>,
)
