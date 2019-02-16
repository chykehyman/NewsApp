package com.hyman.newsapp.views

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.hyman.newsapp.databinding.ViewBodyItemBinding
import com.hyman.newsapp.databinding.ViewHeaderItemBinding
import com.hyman.newsapp.views.models.News

class NewsAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    lateinit var binding: ViewDataBinding
    var newsList = mutableListOf<News>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == ViewType.HEADER_ITEM.num) {
            binding = ViewHeaderItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            HeaderViewHolder(binding as ViewHeaderItemBinding)
        } else {
            binding = ViewBodyItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            BodyViewHolder(binding as ViewBodyItemBinding)
        }
    }

    override fun getItemCount() = newsList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            ViewType.HEADER_ITEM.num -> (holder as HeaderViewHolder).bind(newsList[position])
            ViewType.CONTENT_LIST_ITEM.num -> (holder as BodyViewHolder).bind(newsList[position])
        }
    }

    override fun getItemViewType(position: Int) =
        if (position == 0) ViewType.HEADER_ITEM.num else ViewType.CONTENT_LIST_ITEM.num

    fun updateNewsList(news: MutableList<News>) {
        newsList.addAll(news)
        notifyDataSetChanged()
    }

    inner class HeaderViewHolder(var binding: ViewHeaderItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(news: News) {
            binding.newsModel = news
            binding.executePendingBindings()
        }
    }

    inner class BodyViewHolder(var binding: ViewBodyItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(news: News) {
            binding.newsModel = news
            binding.executePendingBindings()
        }
    }

    enum class ViewType(val num: Int) {
        HEADER_ITEM(num = 0), CONTENT_LIST_ITEM(num = 1)
    }
}