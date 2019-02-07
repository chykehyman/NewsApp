package com.hyman.newsapp.domain.baseViews

import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseFragment<B : ViewDataBinding> : Fragment() {
    private var compositeDisposable: CompositeDisposable? = null
    protected lateinit var binding: B

    @LayoutRes
    protected abstract fun layoutId(): Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, layoutId(), container, false)
        compositeDisposable = CompositeDisposable()
        return binding.root
    }

    fun bind(disposable: Disposable) = compositeDisposable?.add(disposable)

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable?.clear()
        compositeDisposable = null
    }
}