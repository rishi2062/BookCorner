package org.example.project.books.presentation.book_list.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.example.project.books.domain.Book
import org.example.project.books.presentation.book_list.BookListEvents
import org.example.project.books.presentation.book_list.BookListScreen
import org.example.project.books.presentation.book_list.BookListViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun BookListScreenRoot(
    modifier: Modifier = Modifier
) {
//    val viewModel: BookListViewModel = koinViewModel()
    val viewModel = remember { BookListViewModel() }
    val state by viewModel.state.collectAsStateWithLifecycle()

    BookListScreen(
        modifier = modifier,
        state = state
    ){ action ->
        when(action){
            is BookListEvents.onBookClick -> TODO()
            is BookListEvents.onSearchQueryChange -> {
                viewModel.updateSearchQuery(action.query)
            }
            is BookListEvents.onTabSelected -> {
                viewModel.updateTabSelected(action.index)
            }
        }
    }
}