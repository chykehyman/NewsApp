package com.hyman.newsapp.domain.data.api

import com.hyman.newsapp.util.ApiSecret.KEY
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitModule(private val baseUrl: String) : IRetrofitModule {
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val requestInterceptor: Interceptor = Interceptor { chain ->
        val baseRequest = chain.request()
        val url = baseRequest.url()
            .newBuilder()
            .addQueryParameter("api-key", KEY)
            .build()

        chain.proceed(
            baseRequest.newBuilder()
                .url(url)
                .build()
        )
    }

    private val okHttpClient: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(requestInterceptor)
        .addInterceptor(loggingInterceptor)
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .build()

    override fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}