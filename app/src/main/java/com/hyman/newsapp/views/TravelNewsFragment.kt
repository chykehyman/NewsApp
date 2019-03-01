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

class TravelNewsFragment : BaseFragment<FragmentNewsBinding>() {
    override fun layoutId() = R.layout.fragment_news

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

    private fun getAdapter(): NewsAdapter {
        return (binding.rvNewsList.adapter as NewsAdapter)
    }

    private fun getTravelNews(newType: Constants.NewsType) {
        viewModel.getNews(newType)
            .executeOnBackground()
            .subscribe({
                viewModel.progressIsVisible.set(false)
                with(getAdapter()) {
                    updateNewsList(it.news.toMutableList())
                    shareNewsListener(this)
                    moreNewsListener(this)
                }
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