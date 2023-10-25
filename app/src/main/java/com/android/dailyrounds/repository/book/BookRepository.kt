package com.android.dailyrounds.repository.book

import com.android.dailyrounds.core.util.Resource
import com.android.dailyrounds.model.Book
import kotlinx.coroutines.flow.Flow

interface BookRepository {
    suspend fun getBooks(): Flow<Resource<List<Book>>>
}