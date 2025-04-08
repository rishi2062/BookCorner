package org.example.project.books.presentation.book_list

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.example.project.books.presentation.book_list.components.BookList
import org.example.project.books.presentation.book_list.components.BookSearchBar


@Composable
fun BookListScreen(
    state: BookListState,
    modifier: Modifier = Modifier,
    onEvent: (BookListEvents) -> Unit,
) {

    val keyboardController = LocalSoftwareKeyboardController.current
    val pagerState = rememberPagerState { 2 }
    val scrollState = rememberLazyListState()
    val favScrollState = rememberLazyListState()

//    LaunchedEffect(state.searchResult){
//        scrollState.animateScrollToItem(0)
//    }
    LaunchedEffect(state.selectedTabIndex) {
        pagerState.animateScrollToPage(state.selectedTabIndex)
    }

    LaunchedEffect(pagerState.currentPage) {
        onEvent(BookListEvents.onTabSelected(pagerState.currentPage))
    }

    Column(
        modifier = modifier.fillMaxSize()
            .statusBarsPadding()
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BookSearchBar(
            modifier = Modifier
                .widthIn(max = 400.dp)
                .fillMaxWidth()
                .padding(16.dp),
            searchQuery = state.searchQuery,
            onSearchQueryChange = { onEvent(BookListEvents.onSearchQueryChange(it)) },
            onImeClick = {
                keyboardController?.hide()
            }
        )

        Surface(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .border(
                    1.dp,
                    MaterialTheme.colorScheme.primary,
                    RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
                ),
            color = MaterialTheme.colorScheme.secondary,
        ) {

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                TabRow(
                    selectedTabIndex = state.selectedTabIndex,
                    modifier = Modifier.widthIn(max = 700.dp)
                        .fillMaxWidth()
                ) {
                    BookTabs(
                        title = "Search Results",
                        selectedIndex = 0,
                        state = state,
                        onEvent = onEvent
                    )
                    BookTabs(
                        title = "Favorites",
                        selectedIndex = 1,
                        state = state,
                        onEvent = onEvent
                    )
                }

                when {
                    state.isLoading -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(
                                color = Color.White
                            )
                        }
                    }

                    else -> {
                        Spacer(Modifier.height(10.dp))
                        HorizontalPager(
                            state = pagerState, modifier = Modifier.fillMaxWidth().weight(1f)
                        ) { pageIndex ->

                            when (pageIndex) {
                                0 -> {
                                    Box(
                                        modifier = modifier.fillMaxSize(),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        when {
                                            state.books.isEmpty() -> {
                                                Text(
                                                    text = "No books found",
                                                    textAlign = TextAlign.Center,
                                                    style = MaterialTheme.typography.bodyMedium
                                                )
                                            }

                                            else -> {
                                                BookList(
                                                    books = state.books,
                                                    onBookClick = {
                                                        onEvent(BookListEvents.onBookClick(it))
                                                    },
                                                    scrollState = scrollState
                                                )
                                            }
                                        }
                                    }
                                }

                                1 -> {
                                    Box(
                                        modifier = modifier.fillMaxSize(),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        if (state.favorites.isEmpty()) {
                                            Text(
                                                text = "No favorites yet",
                                                textAlign = TextAlign.Center,
                                                style = MaterialTheme.typography.bodyMedium
                                            )
                                        } else {
                                            BookList(
                                                books = state.favorites,
                                                onBookClick = {
                                                    onEvent(BookListEvents.onBookClick(it))
                                                },
                                                scrollState = favScrollState
                                            )
                                        }
                                    }
                                }
                            }


                        }
                    }
                }
            }
        }
    }
}


@Composable
fun BookTabs(
    modifier: Modifier = Modifier,
    selectedIndex: Int = 0,
    title: String,
    state: BookListState,
    onEvent: (BookListEvents) -> Unit
) {
    Tab(
        selected = state.selectedTabIndex == selectedIndex,
        onClick = {
            println("selected index is $selectedIndex")
            onEvent(BookListEvents.onTabSelected(selectedIndex))
        },
        selectedContentColor = MaterialTheme.colorScheme.primary,
        unselectedContentColor = MaterialTheme.colorScheme.secondary

    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(12.dp)
        )
    }
}