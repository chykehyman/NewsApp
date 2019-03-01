package com.hyman.newsapp.domain.data.repository.implementations

import com.hyman.newsapp.domain.data.db.NewsDatabase
import com.hyman.newsapp.domain.data.repository.abstractions.IOfflineRepository
import com.hyman.newsapp.globals.Constants
import com.hyman.newsapp.views.models.NewsResponse

class OfflineRepository(database: NewsDatabase) : IOfflineRepository {
    private val newsDao = database.newsDao()

    override fun saveNews(newsResponse: NewsResponse) {
        with(newsDao) {
            clearNews(newsResponse.section)
            insertNewsResponse(newsResponse)
        }
    }

    override fun getOfflineNews(newsType: Constants.NewsType) =
        newsDao.getNewsByType(newsType.toString().toLowerCase())
}