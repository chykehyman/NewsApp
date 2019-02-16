package com.hyman.newsapp.views


import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.hyman.newsapp.Globals.Constants
import com.hyman.newsapp.R

import com.hyman.newsapp.databinding.FragmentNewsBinding
import com.hyman.newsapp.domain.baseViews.BaseFragment
import com.hyman.newsapp.domain.extentions.addToCompositeDisposable
import com.hyman.newsapp.domain.extentions.executeOnBackground
import com.hyman.newsapp.domain.extentions.isNetworkConnected
import com.hyman.newsapp.domain.extentions.showSnackBar
import org.koin.android.viewmodel.ext.android.viewModel

class HomeNewsFragment : BaseFragment<FragmentNewsBinding>() {
    override fun layoutId() = R.layout.fragment_news
    private val viewModel: NewsViewModel by viewModel()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        binding.viewModel = this@HomeNewsFragment.viewModel
        initRecyclerView()
        getHomeNews(Constants.NewsType.HOME)
        super.onActivityCreated(savedInstanceState)
    }

    private fun initRecyclerView() {
        with(binding.rvNewsList) {
            layoutManager = LinearLayoutManager(context)
            adapter = NewsAdapter()
            setHasFixedSize(true)
        }
    }

    private fun getHomeNews(newType: Constants.NewsType) {
        viewModel.getNewFromApi(newType)
            .executeOnBackground()
            .subscribe({
                (binding.rvNewsList.adapter as NewsAdapter).updateNewsList(it.news.toMutableList())
            }, {
                showSnackBar(
                    if (!isNetworkConnected())
                        getString(R.string.no_network_connection)
                    else
                        it.message!!,
                    getString(R.string.retry)
                ) { getHomeNews(newType) }
            })
            .addToCompositeDisposable(compositeDisposable!!)
    }
}