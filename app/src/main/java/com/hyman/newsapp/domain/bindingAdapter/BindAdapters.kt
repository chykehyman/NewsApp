package com.hyman.newsapp.domain.bindingAdapter

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("isVisible")
fun isVisible(view: View, visible: Boolean) {
    view.visibility = if (visible) View.VISIBLE else View.GONE
}

@BindingAdapter("formatDate")
fun formatDate(view: TextView, publishedDate: String?) {
    publishedDate?.run {
        val date = split("T")[0]
        val month = date.split("-")[1]
        val day = date.split("-")[2]
        val year = date.split("-")[0]
        val newMonthFormat: String = when (month) {
            "1" -> "Jan."
            "2" -> "Feb."
            "3" -> "Mar."
            "4" -> "Apr."
            "5" -> "May."
            "6" -> "Jun."
            "7" -> "Jul."
            "8" -> "Aug."
            "9" -> "Sep."
            "10" -> "Oct."
            "11" -> "Nov."
            else -> "Dec"
        }
        view.text = "$newMonthFormat $day, $year"
    }
}