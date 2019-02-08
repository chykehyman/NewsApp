package com.hyman.newsapp.domain.di

import com.hyman.newsapp.BuildConfig
import com.hyman.newsapp.domain.data.api.IRetrofitModule
import com.hyman.newsapp.domain.data.api.RetrofitModule
import com.hyman.newsapp.domain.data.repository.abstractions.IOnlineRepository
import com.hyman.newsapp.domain.data.repository.abstractions.IRepository
import com.hyman.newsapp.domain.data.repository.implementations.OnlineRepository
import com.hyman.newsapp.domain.data.repository.implementations.Repository
import com.hyman.newsapp.views.NewsViewModel
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.Module
import org.koin.dsl.module.module

val appModule: Module = module {
    single { RetrofitModule(BuildConfig.BASE_URL) as IRetrofitModule }
    single { OnlineRepository(get()) as IOnlineRepository }
    single { Repository(get()) as IRepository }

    viewModel { NewsViewModel(get()) }
}