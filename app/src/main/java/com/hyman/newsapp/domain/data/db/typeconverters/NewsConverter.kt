package com.hyman.newsapp.domain.data.db.typeconverters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.hyman.newsapp.views.models.News
import java.util.*

class NewsConverter {
    private val gson = Gson()

    @TypeConverter
    fun stringToNewsList(dataString: String?): List<News> {
        if (dataString == null) {
            return Collections.emptyList()
        }

        val listType = object : TypeToken<List<News>>() {}.type

        return gson.fromJson(dataString, listType)
    }

    @TypeConverter
    fun newsListToString(dataObject: List<News>): String {
        return gson.toJson(dataObject)
    }
}