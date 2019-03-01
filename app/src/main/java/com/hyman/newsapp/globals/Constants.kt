package com.hyman.newsapp.globals

object Constants {
    enum class NewsType {
        HOME, FOOD, TRAVEL
    }

    const val RESPONSE_TABLE_NAME = "response"
    const val DATABASE_NAME = "news.db"
    const val DEBOUNCE_TIME = 500L
    const val REQUEST_READ_CONTACTS = 124
}