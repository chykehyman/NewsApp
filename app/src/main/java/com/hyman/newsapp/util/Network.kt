package com.hyman.newsapp.util

import android.content.Context
import android.net.ConnectivityManager

fun isConnectedToNetwork(context: Context) : Boolean {
    val systemService = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    return systemService.activeNetworkInfo?.isConnected ?: false
}