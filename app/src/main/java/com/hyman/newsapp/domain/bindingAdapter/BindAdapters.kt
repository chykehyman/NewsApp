package com.hyman.newsapp.domain.bindingAdapter

import androidx.databinding.BindingAdapter
import android.view.View

@BindingAdapter("isVisible")
fun isVisible(view: View, visible: Boolean) {
    view.visibility = if (visible) View.VISIBLE else View.GONE
}