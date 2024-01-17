package com.dicoding.githubuser.data.retrofit

import com.dicoding.githubuser.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = BuildConfig.BASE_URL

    private val authInterceptor = Interceptor { chain ->
        val req = chain.request()
        val key = BuildConfig.KEY
        val requestHeaders = req.newBuilder()
            .addHeader("Authorization", key)
            .build()
        chain.proceed(requestHeaders)
    }

    val client = OkHttpClient.Builder()
        .addInterceptor(authInterceptor)
        .build()

    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    val apiInstance = retrofit.create(ApiService::class.java)
}