package com.hyman.newsapp.domain.baseViews

import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseActivity<T : ViewDataBinding>: AppCompatActivity() {
    protected lateinit var binding: T
    var compositeDisposable: CompositeDisposable? = null
    @LayoutRes
    protected abstract fun layoutResId(): Int

    private fun bindContentView(layoutId: Int) {
        binding = DataBindingUtil.setContentView(this, layoutId)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindContentView(layoutResId())
        compositeDisposable = CompositeDisposable()
    }

    fun bind(disposable: Disposable) {
        compositeDisposable?.add(disposable)
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable?.clear()
        compositeDisposable = null
    }
}