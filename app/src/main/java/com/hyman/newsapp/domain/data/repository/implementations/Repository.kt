package com.hyman.newsapp.domain.data.repository.implementations

import com.hyman.newsapp.Globals.Constants
import com.hyman.newsapp.domain.data.repository.abstractions.IOnlineRepository
import com.hyman.newsapp.domain.data.repository.abstractions.IRepository
import com.hyman.newsapp.views.models.NewsResponse
import io.reactivex.Observable

class Repository(private val onlineRepository: IOnlineRepository) : IRepository {
    override fun getNews(newsType: Constants.NewsType): Observable<NewsResponse> {
        return onlineRepository.getNews(newsType)
    }
}