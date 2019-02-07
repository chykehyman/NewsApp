package com.hyman.newsapp.views


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

import com.hyman.newsapp.R


class HomeNewsFragment : Fragment() {

    companion object {
        fun newInstance() = HomeNewsFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home_news, container, false)
    }
}