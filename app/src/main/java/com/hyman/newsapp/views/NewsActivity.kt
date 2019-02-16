package com.hyman.newsapp.views

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.hyman.newsapp.R
import com.hyman.newsapp.databinding.ActivityNewsBinding
import com.hyman.newsapp.domain.baseViews.BaseActivity
import kotlinx.android.synthetic.main.activity_news.*

class NewsActivity : BaseActivity<ActivityNewsBinding>() {
    override fun layoutResId() = R.layout.activity_news

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(tb_app_toolbar)

        navController = Navigation.findNavController(this, R.id.fragment_nav_host)
        bn_navigation.setupWithNavController(navController)

        NavigationUI.setupActionBarWithNavController(this, navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, null)
    }
}