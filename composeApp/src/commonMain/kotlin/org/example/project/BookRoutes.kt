package org.example.project

import kotlinx.serialization.Serializable

sealed interface BookRoutes {

    @Serializable
    data object BookGraph : BookRoutes

    @Serializable
    data object Home : BookRoutes

    @Serializable
    data class BookDetails(val bookId: String?) : BookRoutes
}