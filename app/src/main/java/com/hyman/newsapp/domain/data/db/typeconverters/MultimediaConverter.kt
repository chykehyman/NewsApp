package com.hyman.newsapp.domain.data.db.typeconverters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.hyman.newsapp.views.models.Multimedia
import java.util.*

class MultimediaConverter {
    private val gson = Gson()

    @TypeConverter
    fun stringToMediaList(dataString: String?): List<Multimedia> {
        if (dataString == null) {
            return Collections.emptyList()
        }

        val listType = object : TypeToken<List<Multimedia>>() {}.type

        return gson.fromJson(dataString, listType)
    }

    @TypeConverter
    fun mediaListToString(dataObject: List<Multimedia>): String {
        return gson.toJson(dataObject)
    }
}