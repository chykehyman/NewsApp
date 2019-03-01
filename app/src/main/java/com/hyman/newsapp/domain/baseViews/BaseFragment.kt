package com.hyman.newsapp.domain.baseViews

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.hyman.newsapp.R
import com.hyman.newsapp.domain.extentions.showSnackBar
import com.hyman.newsapp.globals.Constants
import com.hyman.newsapp.views.NewsAdapter
import com.hyman.newsapp.views.NewsViewModel
import com.hyman.newsapp.views.contactprovider.ContactDialogFragment
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import org.koin.android.viewmodel.ext.android.viewModel


abstract class BaseFragment<B : ViewDataBinding> : Fragment() {
    var compositeDisposable: CompositeDisposable? = null
    protected lateinit var binding: B

    val viewModel: NewsViewModel by viewModel()

    private var newsUrl: String? = null

    @LayoutRes
    protected abstract fun layoutId(): Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, layoutId(), container, false)
        compositeDisposable = CompositeDisposable()
        return binding.root
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == Constants.REQUEST_READ_CONTACTS) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                displayContactsDialog()
            } else {
                showSnackBar("You need to allow contacts access permission to share a news")
            }
        }
    }

    fun bind(disposable: Disposable) = compositeDisposable?.add(disposable)

    private fun requestContactAccessPermission() {
        val permission = Manifest.permission.READ_CONTACTS

        if (ContextCompat.checkSelfPermission(context!!, permission) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(permission), Constants.REQUEST_READ_CONTACTS)
        } else {
            displayContactsDialog()
        }
    }

    private fun displayContactsDialog() {
        val args = Bundle()
        args.putString("newsUrl", newsUrl)
        val dialog = ContactDialogFragment.newInstance()

        dialog.arguments = args
        dialog.show(activity!!.supportFragmentManager, "contact dialog")
    }

    fun shareNewsListener(newsAdapter: NewsAdapter) {
        newsAdapter.shareNewsItemClick = {
            newsUrl = it.shortUrl
            requestContactAccessPermission()
        }
    }

    fun moreNewsListener(newsAdapter: NewsAdapter) {
        newsAdapter.moreNewsItemClick = {
            openFullNewsInBrowser(it)
        }
    }

    private fun openFullNewsInBrowser(url: String) {
        val uri = Uri.parse(url)
        val intent = Intent(Intent.ACTION_VIEW)
            .addCategory(Intent.CATEGORY_BROWSABLE)
            .setData(uri)

        if (intent.resolveActivity(activity!!.packageManager) != null) {
            startActivity(intent)
        } else {
            showSnackBar(resources.getString(R.string.no_browser_installed))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable?.clear()
        compositeDisposable = null
    }
}