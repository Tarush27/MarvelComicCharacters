package com.tarush27.networking

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

object OkHttpLoginInterceptor {
    private val httpLoggingInterceptor = HttpLoggingInterceptor()
        .setLevel(HttpLoggingInterceptor.Level.BASIC)
    val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(httpLoggingInterceptor)
        .build()

}