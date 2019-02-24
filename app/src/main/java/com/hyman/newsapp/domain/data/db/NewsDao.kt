package com.hyman.newsapp.domain.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.hyman.newsapp.globals.Constants.RESPONSE_TABLE_NAME
import com.hyman.newsapp.views.models.NewsResponse
import io.reactivex.Flowable

@Dao
interface NewsDao {
    @Insert(onConflict = REPLACE)
    fun insertNewsResponse(response: NewsResponse)

    @Query("SELECT * FROM $RESPONSE_TABLE_NAME WHERE section=:type")
    fun getNewsByType(type: String): Flowable<NewsResponse>

    @Query("DELETE FROM $RESPONSE_TABLE_NAME WHERE section=:type")
    fun clearNews(type: String)
}