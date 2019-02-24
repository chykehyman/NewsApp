package com.hyman.newsapp.domain.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.hyman.newsapp.domain.data.db.typeconverters.MultimediaConverter
import com.hyman.newsapp.domain.data.db.typeconverters.NewsConverter
import com.hyman.newsapp.views.models.NewsResponse

@Database(entities = [NewsResponse::class], version = 1)
@TypeConverters(NewsConverter::class, MultimediaConverter::class)
abstract class NewsDatabase : RoomDatabase() {
    abstract fun newsDao(): NewsDao
}