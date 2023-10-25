package com.android.dailyrounds.di

import com.android.dailyrounds.network.APIClient
import com.android.dailyrounds.network.APIService
import com.android.dailyrounds.repository.book.BookRepository
import com.android.dailyrounds.repository.book.BookRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun getAPIService() = APIClient().apiService

    @Provides
    @Singleton
    fun provideBookRepository(
        apiService: APIService,
    ): BookRepository =
        BookRepositoryImpl(apiService)

}