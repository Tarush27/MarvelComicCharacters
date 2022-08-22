package com.tarush27.networking

import com.tarush27.marvelConstants.MARVEL_BASE_LINK
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    val retrofit = Retrofit.Builder()
        .client(OkHttpLoginInterceptor.okHttpClient)
        .baseUrl(MARVEL_BASE_LINK)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val marvelsApiService = retrofit.create(MarvelsApiService::class.java)
}