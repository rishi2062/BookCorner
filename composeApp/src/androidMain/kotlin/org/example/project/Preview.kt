package org.example.project

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import org.example.project.books.presentation.book_list.navigation.BookListScreenRoot

@Preview
@Composable
fun BookListScreenPreview(modifier: Modifier = Modifier) {
    BookListScreenRoot()
}