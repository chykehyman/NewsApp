package com.hyman.newsapp.views

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.ViewModel
import com.hyman.newsapp.domain.data.repository.abstractions.IRepository
import com.hyman.newsapp.globals.Constants
import com.hyman.newsapp.globals.Constants.DEBOUNCE_TIME
import com.hyman.newsapp.views.models.NewsResponse
import io.reactivex.Flowable
import java.util.concurrent.TimeUnit

class NewsViewModel(private val repository: IRepository) : ViewModel() {
    val progressIsVisible = ObservableBoolean(false)
    val hasContacts = ObservableBoolean()

    fun getNews(newsType: Constants.NewsType): Flowable<NewsResponse> {
        progressIsVisible.set(true)
        return repository.getNews(newsType)
            .debounce(DEBOUNCE_TIME, TimeUnit.MILLISECONDS)
            .flatMap {
                Flowable.just(it)
            }
            .onErrorResumeNext { throwable: Throwable ->
                Flowable.error(Exception(throwable))
            }
            .doOnTerminate {
                progressIsVisible.set(false)
            }
    }
}