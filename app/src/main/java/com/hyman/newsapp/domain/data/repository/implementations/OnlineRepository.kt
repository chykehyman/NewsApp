package com.hyman.newsapp.domain.data.repository.implementations

import com.hyman.newsapp.Globals.Constants
import com.hyman.newsapp.domain.data.api.ApiService
import com.hyman.newsapp.domain.data.api.IRetrofitModule
import com.hyman.newsapp.domain.data.repository.abstractions.IOnlineRepository
import com.hyman.newsapp.views.models.NewsResponse
import io.reactivex.Observable

class OnlineRepository(retrofitModule: IRetrofitModule) : IOnlineRepository{
    private val retrofit = retrofitModule.getRetrofit()
    private val apiService = retrofit.create(ApiService::class.java)

    override fun getNews(newsType: Constants.NewsType): Observable<NewsResponse> {
        return when(newsType) {
            Constants.NewsType.HOME -> apiService.getHomeNews()
            Constants.NewsType.FOOD -> apiService.getFoodNews()
            Constants.NewsType.TRAVEL -> apiService.getTravelNews()
        }
    }
}