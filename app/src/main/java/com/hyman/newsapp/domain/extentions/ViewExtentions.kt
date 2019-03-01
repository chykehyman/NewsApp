package com.hyman.newsapp.domain.extentions

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.hyman.newsapp.util.isConnectedToNetwork

fun Fragment.showSnackBar(message: String, actionMessage: String, action: (v: View?) -> Unit) {
    view?.let {
        Snackbar.make(it, message, Snackbar.LENGTH_INDEFINITE)
            .setAction(actionMessage, action)
            .show()
    }
}

fun Fragment.showSnackBar(message: String) {
    view?.let {
        Snackbar.make(it, message, Snackbar.LENGTH_LONG)
            .show()
    }
}

fun Fragment.isNetworkConnected(): Boolean =
    context?.let { isConnectedToNetwork(it) } ?: false

fun AppCompatActivity.isNetworkConnected(): Boolean =
    applicationContext?.let { isConnectedToNetwork(it) } ?: false