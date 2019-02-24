package com.hyman.newsapp

import android.app.Application
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.stetho.Stetho
import com.hyman.newsapp.domain.di.appModule
import org.koin.android.ext.android.startKoin
import timber.log.Timber

class NewsApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        startKoin(this, listOf(appModule))
        Fresco.initialize(this)
        Stetho.initializeWithDefaults(this)
    }
}