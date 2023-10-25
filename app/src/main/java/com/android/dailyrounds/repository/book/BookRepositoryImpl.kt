package com.android.dailyrounds.repository.book

import com.android.dailyrounds.core.util.Resource
import com.android.dailyrounds.model.Book
import com.android.dailyrounds.network.APIService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class BookRepositoryImpl(
    private val apiService: APIService,
) : BookRepository {

    override suspend fun getBooks(): Flow<Resource<List<Book>>> = flow {
        emit(Resource.Loading())
        try {
            val result = apiService.getBooks()
            emit(Resource.Success(result))
        } catch (e: HttpException) {
            emit(
                Resource.Error(
                    message = "Try again Later!",
                    data = null,
                )
            )
        } catch (e: IOException) {
            emit(
                Resource.Error(
                    message = "Check you internet connection!",
                    data = null,
                )
            )
        }
    }
}