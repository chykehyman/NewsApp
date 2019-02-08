package com.hyman.newsapp.views

import androidx.lifecycle.ViewModel
import com.hyman.newsapp.Globals.Constants
import com.hyman.newsapp.domain.data.repository.abstractions.IRepository
import com.hyman.newsapp.views.models.NewsResponse
import io.reactivex.Observable
import timber.log.Timber

class NewsViewModel(private val repository: IRepository) : ViewModel() {

    fun getNewFromApi(newsType: Constants.NewsType): Observable<NewsResponse> {
        return repository.getNews(newsType)
            .flatMap {
                Observable.just(it)
            }
            .doOnError {
                Timber.e("ERROR: ${it.message}")
            }
    }
}