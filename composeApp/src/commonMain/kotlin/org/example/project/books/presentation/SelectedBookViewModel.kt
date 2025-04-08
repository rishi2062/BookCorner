package org.example.project.books.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.example.project.books.domain.Book

class SelectedBookViewModel : ViewModel() {

    private val _selectedBook = MutableStateFlow<Book?>(null)
    val selectedBook = _selectedBook.asStateFlow()


    fun onSelectBook(book : Book?){
        println("Selected Book : $book")
        _selectedBook.value = book
    }

    fun clearSelectedBook(){
        _selectedBook.value = null
    }
}