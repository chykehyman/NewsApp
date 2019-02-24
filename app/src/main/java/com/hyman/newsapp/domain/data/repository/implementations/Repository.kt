package com.hyman.newsapp.domain.data.repository.implementations

import com.hyman.newsapp.domain.data.repository.abstractions.IOfflineRepository
import com.hyman.newsapp.domain.data.repository.abstractions.IOnlineRepository
import com.hyman.newsapp.domain.data.repository.abstractions.IRepository
import com.hyman.newsapp.globals.Constants
import com.hyman.newsapp.views.models.NewsResponse
import io.reactivex.Flowable

class Repository(private val offlineRepository: IOfflineRepository, private val onlineRepository: IOnlineRepository) :
    IRepository {
    override fun getNews(newsType: Constants.NewsType): Flowable<NewsResponse> {
        return Flowable.concatArrayEager(
            offlineRepository.getOfflineNews(newsType)
                .filter {
                    it.news.isNotEmpty()
                },
            onlineRepository.getOnlineNews(newsType)
                .materialize()
                .filter {
                    !it.isOnError
                }
                .dematerialize<NewsResponse>()
                .doOnNext {
                    offlineRepository.saveNews(it)
                }
        )
    }
}