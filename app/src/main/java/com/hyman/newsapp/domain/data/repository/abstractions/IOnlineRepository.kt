package com.hyman.newsapp.domain.data.repository.abstractions

import com.hyman.newsapp.globals.Constants
import com.hyman.newsapp.views.models.NewsResponse
import io.reactivex.Flowable

interface IOnlineRepository {
    fun getOnlineNews(newsType: Constants.NewsType): Flowable<NewsResponse>
}