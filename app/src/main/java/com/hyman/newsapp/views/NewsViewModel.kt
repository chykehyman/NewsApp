package com.hyman.newsapp.views

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.ViewModel
import com.hyman.newsapp.Globals.Constants
import com.hyman.newsapp.domain.data.repository.abstractions.IRepository
import com.hyman.newsapp.views.models.NewsResponse
import io.reactivex.Observable
import timber.log.Timber

class NewsViewModel(private val repository: IRepository) : ViewModel() {
    val progressIsVisible = ObservableBoolean(false)

    fun getNewFromApi(newsType: Constants.NewsType): Observable<NewsResponse> {
        progressIsVisible.set(true)
        return repository.getNews(newsType)
            .flatMap {
                Observable.just(it)
            }
            .onErrorResumeNext { throwable: Throwable ->
                Timber.e("ERROR: ${throwable.message}")
                Observable.error(Exception(throwable))
            }
            .doAfterTerminate {
                progressIsVisible.set(false)
            }
    }
}