package com.hyman.newsapp.views


import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.hyman.newsapp.R
import com.hyman.newsapp.databinding.FragmentNewsBinding
import com.hyman.newsapp.domain.baseViews.BaseFragment
import com.hyman.newsapp.domain.extentions.addToCompositeDisposable
import com.hyman.newsapp.domain.extentions.executeOnBackground
import com.hyman.newsapp.domain.extentions.isNetworkConnected
import com.hyman.newsapp.domain.extentions.showSnackBar
import com.hyman.newsapp.globals.Constants
import org.koin.android.viewmodel.ext.android.viewModel

class TravelNewsFragment : BaseFragment<FragmentNewsBinding>() {
    override fun layoutId() = R.layout.fragment_news
    private val viewModel: NewsViewModel by viewModel()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        binding.viewModel = this@TravelNewsFragment.viewModel
        initRecyclerView()
        getTravelNews(Constants.NewsType.TRAVEL)
        super.onActivityCreated(savedInstanceState)
    }

    private fun initRecyclerView() {
        with(binding.rvNewsList) {
            layoutManager = LinearLayoutManager(context)
            adapter = NewsAdapter()
            setHasFixedSize(true)
        }
    }

    private fun getTravelNews(newType: Constants.NewsType) {
        viewModel.getNews(newType)
            .executeOnBackground()
            .subscribe({
                viewModel.progressIsVisible.set(false)
                (binding.rvNewsList.adapter as NewsAdapter).updateNewsList(it.news.toMutableList())
            }, {
                showSnackBar(
                    if (!isNetworkConnected())
                        getString(R.string.no_network_connection)
                    else
                        it.message!!,
                    getString(R.string.retry)
                ) { getTravelNews(newType) }
            }).addToCompositeDisposable(compositeDisposable!!)
    }
}