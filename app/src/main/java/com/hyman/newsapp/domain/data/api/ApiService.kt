package com.hyman.newsapp.domain.data.api

import com.hyman.newsapp.views.models.NewsResponse
import io.reactivex.Observable
import retrofit2.http.GET

interface ApiService {
    @GET("home.json")
    fun getHomeNews(): Observable<NewsResponse>

    @GET("food.json")
    fun getFoodNews(): Observable<NewsResponse>

    @GET("travel.json")
    fun getTravelNews(): Observable<NewsResponse>
}