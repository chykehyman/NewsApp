package com.hyman.newsapp.domain.data.api

import retrofit2.Retrofit

interface IRetrofitModule {
    fun getRetrofit(): Retrofit
}