package org.example.project

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import androidx.navigation.toRoute
import org.example.project.books.presentation.SelectedBookViewModel
import org.example.project.books.presentation.book_detail.navigation.bookDetailsScreen
import org.example.project.books.presentation.book_list.navigation.bookListScreen
import org.example.project.core.presentation.sharedViewModel

@Composable
fun BookMainUi(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = BookRoutes.BookGraph
    ) {
        navigation<BookRoutes.BookGraph>(
            startDestination = BookRoutes.Home
        ) {
            bookListScreen(navController)
            bookDetailsScreen(navController)

        }
    }
}