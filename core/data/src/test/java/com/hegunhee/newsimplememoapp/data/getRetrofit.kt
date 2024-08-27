package com.hegunhee.newsimplememoapp.data

import com.hegunhee.newsimplememoapp.data.api.CategoryApi
import com.hegunhee.newsimplememoapp.data.api.MemoApi
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit

fun Json.provideConverterFactory(
) : Converter.Factory {
    return this.asConverterFactory("application/json".toMediaType())
}

fun provideRetrofit(
    converterFactory: Converter.Factory = provideJson().provideConverterFactory(),
    okHttpClient: OkHttpClient = provideOkHttpClient()
) : Retrofit {
    return Retrofit.Builder()
        .baseUrl("http://localhost:8080")
        .addConverterFactory(converterFactory)
        .client(okHttpClient)
        .build()
}

fun provideMemoApi() : MemoApi {
    return provideRetrofit().create(MemoApi::class.java)
}

fun provideCategoryApi() : CategoryApi {
    return provideRetrofit().create(CategoryApi::class.java)
}

fun provideOkHttpClient() : OkHttpClient {
    val interceptor = HttpLoggingInterceptor()
    if(BuildConfig.DEBUG) {
        interceptor.level = HttpLoggingInterceptor.Level.BODY
    } else {
        interceptor.level = HttpLoggingInterceptor.Level.NONE
    }

    return OkHttpClient.Builder()
        .addNetworkInterceptor(interceptor)
        .build()
}

fun provideJson() : Json = Json {
    ignoreUnknownKeys = true
}