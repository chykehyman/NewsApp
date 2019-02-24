package com.hyman.newsapp.views.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.hyman.newsapp.globals.Constants.RESPONSE_TABLE_NAME

@Entity(tableName = RESPONSE_TABLE_NAME, indices = [Index("section")])
data class NewsResponse(
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @ColumnInfo(name = "section")
    val section: String,

    @SerializedName("results")
    var news: List<News>
)