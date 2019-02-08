package com.hyman.newsapp.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.hyman.newsapp.Globals.Constants
import com.hyman.newsapp.R
import com.hyman.newsapp.domain.extentions.addToCompositeDisposable
import com.hyman.newsapp.domain.extentions.executeOnBackground
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_news.*
import org.koin.android.viewmodel.ext.android.viewModel
import timber.log.Timber

class NewsActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private val viewModel: NewsViewModel by viewModel()
    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)

        setSupportActionBar(tb_app_toolbar)

        navController = Navigation.findNavController(this, R.id.fragment_nav_host)
        bn_navigation.setupWithNavController(navController)

        getNewItems(Constants.NewsType.FOOD)

        NavigationUI.setupActionBarWithNavController(this, navController)
    }

    private fun getNewItems(food: Constants.NewsType) {
        viewModel.getNewFromApi(food)
            .executeOnBackground()
            .subscribe({
                Timber.e("NEWS RESPONSE: ${it.news.size}")
            }, {
                Timber.e("ERROR: ${it.message}")
            })
            .addToCompositeDisposable(compositeDisposable)
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, null)
    }
}