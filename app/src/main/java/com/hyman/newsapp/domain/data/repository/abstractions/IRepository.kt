package com.hyman.newsapp.domain.data.repository.abstractions

import com.hyman.newsapp.Globals.Constants
import com.hyman.newsapp.views.models.NewsResponse
import io.reactivex.Observable

interface IRepository {
    fun getNews(newsType : Constants.NewsType) : Observable<NewsResponse>
}