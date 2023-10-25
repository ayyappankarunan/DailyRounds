package com.android.dailyrounds.network

import com.android.dailyrounds.model.Book
import retrofit2.http.GET

interface APIService {
    @GET("b/CNGI")
    suspend fun getBooks(): List<Book>
}