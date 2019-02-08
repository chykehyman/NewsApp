package com.hyman.newsapp.views.models

import com.google.gson.annotations.SerializedName

data class Result(
    val abstract: String,
    @SerializedName("byline") val author: String,
    val multimedia: List<Multimedia>,
    @SerializedName("published_date")
    val publishedDate: String,
    @SerializedName("short_url")
    val shortUrl: String,
    val title: String
)