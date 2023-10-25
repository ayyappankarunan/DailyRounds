package com.android.dailyrounds.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class APIClient {
    val apiService: APIService by lazy {
        Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(APIService::class.java)
    }

    companion object {
        const val BASE_URL = "https://www.jsonkeeper.com/"
    }
}