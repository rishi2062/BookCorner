package org.example.project.books.presentation.book_list.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import org.example.project.BookRoutes
import org.example.project.books.data.network.KtorRemoteBookDataSource
import org.example.project.books.data.network.RemoteBookDataSource
import org.example.project.books.data.repository.SearchedBookRepo
import org.example.project.books.domain.Book
import org.example.project.books.domain.BookRepository
import org.example.project.books.presentation.SelectedBookViewModel
import org.example.project.books.presentation.book_list.BookListEvents
import org.example.project.books.presentation.book_list.BookListScreen
import org.example.project.books.presentation.book_list.BookListViewModel
import org.example.project.core.data.HttpClientFactory
import org.example.project.core.presentation.sharedViewModel
import org.koin.compose.viewmodel.koinViewModel


fun NavGraphBuilder.bookListScreen(
    navController: NavController
){
    composable<BookRoutes.Home>(){
        val selectedViewModel : SelectedBookViewModel = it.sharedViewModel(navController)
        BookListScreenRoot(navController = navController,
            selectedBookViewModel = selectedViewModel)
    }
}
@Composable
fun BookListScreenRoot(
    modifier: Modifier = Modifier,
    navController: NavController = rememberNavController(),
    selectedBookViewModel: SelectedBookViewModel
) {
    val viewModel: BookListViewModel = koinViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()

    println("SelectedBookVMINstance :  ${selectedBookViewModel}")

    LaunchedEffect(true){
        selectedBookViewModel.onSelectBook(null)
    }

    BookListScreen(
        modifier = modifier,
        state = state
    ){ action ->
        when(action){
            is BookListEvents.onBookClick -> {
                selectedBookViewModel.onSelectBook(action.book)
                navController.navigate(BookRoutes.BookDetails(action.book.id))
            }
            is BookListEvents.onSearchQueryChange -> {
                viewModel.updateSearchQuery(action.query)
            }
            is BookListEvents.onTabSelected -> {
                viewModel.updateTabSelected(action.index)
            }
        }
    }
}