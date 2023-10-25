package com.android.dailyrounds.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.dailyrounds.core.util.Resource
import com.android.dailyrounds.model.Book
import com.android.dailyrounds.repository.book.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.ZoneId
import javax.inject.Inject

@HiltViewModel
class BookViewModel @Inject constructor(private val repository: BookRepository) : ViewModel() {

    private val _bookState = mutableStateOf(BookState())
    val bookState: State<BookState> = _bookState


    var books: List<Book>? = null


    init {
        getBooks()
    }

    private fun getBooks() {
        viewModelScope.launch {
            repository.getBooks().collectLatest { result ->
                when (result) {
                    is Resource.Success -> {
                        books = result.data?.map {
                            val dt = Instant.ofEpochSecond(it.publishedChapterDate.toLong())
                                .atZone(ZoneId.systemDefault())
                                .toLocalDateTime()
                            it.copy(
                                publishedChapterDate = dt.year
                            )
                        }?.sortedBy { it.publishedChapterDate }
                        _bookState.value = _bookState.value.copy(
                            books = books?.groupBy { it.publishedChapterDate },
                            isLoading = false
                        )
                    }

                    is Resource.Loading -> {
                        _bookState.value = _bookState.value.copy(
                            books = null, isLoading = true
                        )
                    }

                    is Resource.Error -> {
                        _bookState.value = _bookState.value.copy(
                            books = null, isLoading = false
                        )
                    }
                }
            }
        }
    }
}

data class BookState(
    val books: Map<Int, List<Book>>? = null,
    val isLoading: Boolean = false,
)