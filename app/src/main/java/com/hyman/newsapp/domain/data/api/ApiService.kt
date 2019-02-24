package com.hyman.newsapp.domain.data.api

import com.hyman.newsapp.views.models.NewsResponse
import io.reactivex.Flowable
import retrofit2.http.GET

interface ApiService {
    @GET("home.json")
    fun getHomeNews(): Flowable<NewsResponse>

    @GET("food.json")
    fun getFoodNews(): Flowable<NewsResponse>

    @GET("travel.json")
    fun getTravelNews(): Flowable<NewsResponse>
}