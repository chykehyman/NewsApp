package com.hyman.newsapp.views.models

import com.google.gson.annotations.SerializedName

data class NewsResponse(
    @SerializedName("results") val news: List<News>
)