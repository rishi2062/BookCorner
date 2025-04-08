package org.example.project.books.presentation.book_detail.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import org.example.project.BookRoutes
import org.example.project.books.presentation.SelectedBookViewModel
import org.example.project.books.presentation.book_detail.BookDetailEvents
import org.example.project.books.presentation.book_detail.BookDetailState
import org.example.project.books.presentation.book_detail.BookDetailsViewModel
import org.example.project.books.presentation.book_detail.components.BookChip
import org.example.project.books.presentation.book_detail.components.BookDetailsScreen
import org.example.project.books.presentation.book_detail.components.ChipSize
import org.example.project.books.presentation.book_detail.components.TitledContent
import org.example.project.core.presentation.sharedViewModel
import org.koin.compose.viewmodel.koinViewModel
import kotlin.math.round

fun NavGraphBuilder.bookDetailsScreen(
    navController: NavController
) {
    composable<BookRoutes.BookDetails> {
        val viewModel: BookDetailsViewModel = koinViewModel()
        val args = it.toRoute<BookRoutes.BookDetails>()
        val selectedBookViewModel = it.sharedViewModel<SelectedBookViewModel>(navController)
        val selectedBook by selectedBookViewModel.selectedBook.collectAsStateWithLifecycle()
        val state by viewModel.state.collectAsStateWithLifecycle()
        println("SelectedBookVMINstance in home:  ${selectedBookViewModel}")

        LaunchedEffect(selectedBook) {
            selectedBook?.let {
                viewModel.updateSelectedBook(it)
            }
        }
        BookDetailsRoot(state = state) { action ->
            when (action) {
                BookDetailEvents.onBackClick -> {
                    navController.navigateUp()
                }

                BookDetailEvents.onFavoriteClick -> {
                }

                is BookDetailEvents.onSelectedBookChange -> {
                    viewModel.updateSelectedBook(action.book)
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun BookDetailsRoot(
    state: BookDetailState,
    onEvents: (BookDetailEvents) -> Unit
) {
    BookDetailsScreen(
        modifier = Modifier.fillMaxSize(),
        imageUrl = state.book?.imageUrl,
        isFavorite = state.isFavorite,
        onBackClick = {
            onEvents(BookDetailEvents.onBackClick)
        },
        onFavoriteClick = {
            onEvents(BookDetailEvents.onFavoriteClick)
        },
        content = {
            state.book?.let {
                Column(
                    modifier = Modifier.widthIn(max = 700.dp)
                        .fillMaxWidth()
                        .padding(vertical = 16.dp, horizontal = 24.dp)
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = state.book.title ?: "",
                        style = MaterialTheme.typography.headlineSmall,
                        textAlign = TextAlign.Center
                    )

                    state.book.authors?.map { it.name }?.joinToString()?.let { authors ->
                        Text(
                            text = authors,
                            style = MaterialTheme.typography.titleMedium,
                            textAlign = TextAlign.Center
                        )
                    }
                    Row(
                        modifier = Modifier.padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        state.book.averageRating?.let { rating ->

                            TitledContent(
                                title = "Rating"
                            ) {
                                BookChip {
                                    Text(
                                        text = "${round((rating * 10) / 10.0)}",
                                        style = MaterialTheme.typography.titleMedium,
                                        textAlign = TextAlign.Center
                                    )

                                    Icon(
                                        imageVector = Icons.Filled.Star,
                                        contentDescription = "Rating",
                                        tint = Color.Yellow
                                    )
                                }
                            }

                        }

                        state.book.numPages?.let { pageCount ->
                            TitledContent(
                                title = "Pages"
                            ) {
                                BookChip {
                                    Text(
                                        text = "$pageCount",
                                        style = MaterialTheme.typography.titleMedium,
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }
                        }


                        state.book.languages.takeIf { it?.isNotEmpty() == true }?.let { languages ->
                            TitledContent(
                                title = "Languages",
                                modifier = Modifier.padding(vertical = 8.dp)
                            ) {
                                FlowRow(
                                    horizontalArrangement = Arrangement.Center,
                                    modifier = Modifier.wrapContentSize(Alignment.Center)
                                ) {
                                    languages.forEach { language ->
                                        BookChip(
                                            chipSize = ChipSize.SMALL,
                                            modifier = Modifier.padding(2.dp)
                                        ) {
                                            Text(
                                                text = language.uppercase(),
                                                style = MaterialTheme.typography.bodyMedium,
                                                color = Color.White
                                            )
                                        }
                                    }
                                }

                            }
                        }
                    }
                    Text(
                        text = "Synopsis",
                        style = MaterialTheme.typography.titleLarge,
                        textAlign = TextAlign.Start,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 24.dp, bottom = 8.dp)
                    )

                    if (state.isLoading) {
                        Box(
                            modifier = Modifier.fillMaxWidth()
                                .weight(1f),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    } else {
                        val desc = state.book.description.takeIf { !it.isNullOrBlank() }
                            ?: "No Description Found"

                        Text(
                            text = desc,
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.Justify,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }

                }
            }
        }
    )

}