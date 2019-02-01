package com.hyman.newsapp.domain.extentions

import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.hyman.newsapp.util.isConnectedToNetwork

fun Fragment.showSnackBar(message: String, actionMessage: String, action: (v: View?) -> Unit) {
    view?.let {
        Snackbar.make(it, message, Snackbar.LENGTH_INDEFINITE)
            .setAction(actionMessage, action)
            .show()
    }
}

fun Fragment.isNetworkConnected(): Boolean =
    context?.let { isConnectedToNetwork(it) } ?: false

fun AppCompatActivity.isNetworkConnected(): Boolean =
    applicationContext?.let { isConnectedToNetwork(it) } ?: false