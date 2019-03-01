package com.hyman.newsapp.views.contactprovider

import android.app.Dialog
import android.content.ContentResolver
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.hyman.newsapp.R
import com.hyman.newsapp.databinding.FragmentContactBinding
import com.hyman.newsapp.views.NewsViewModel
import com.hyman.newsapp.views.models.Contact
import io.reactivex.disposables.CompositeDisposable
import org.koin.android.viewmodel.ext.android.viewModel


class ContactDialogFragment : DialogFragment() {
    private var compositeDisposable: CompositeDisposable? = null
    lateinit var binding: FragmentContactBinding

    private val viewModel: NewsViewModel by viewModel()
    private val usersContactList = ArrayList<Contact>()
    private var selectedNewsUrl: String? = null

    companion object {
        fun newInstance(): ContactDialogFragment = ContactDialogFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        selectedNewsUrl = arguments?.getString("newsUrl")
        getUserContacts()
        super.onCreate(savedInstanceState)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = activity?.let { AlertDialog.Builder(it) }
        val layoutInflater: LayoutInflater = activity!!.layoutInflater

        binding = FragmentContactBinding.inflate(layoutInflater, null, false)

//        Timber.e("IN CONTACT DIALOG $newsUrl")

        builder?.run {
            setTitle(R.string.title_contact)
            setView(binding.root)

        }.apply {
            return this!!.create()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.viewModel = this@ContactDialogFragment.viewModel
        initRecyclerView()
        compositeDisposable = CompositeDisposable()

        if (usersContactList.size == 0)
            viewModel.hasContacts.set(false)
        else {
            viewModel.hasContacts.set(true)
            showUserContacts(usersContactList)
        }
    }

    private fun initRecyclerView() {
        with(binding.rvContactList) {
            layoutManager = LinearLayoutManager(context)
            adapter = ContactAdapter {
                sendMessage(it)
            }
        }
    }

    private fun getAdapter(): ContactAdapter {
        return (binding.rvContactList.adapter as ContactAdapter)
    }

    private fun sendMessage(contact: Contact) {
        val textMessage =
            "Hi ${contact.name}, you were requested to view this news page: $selectedNewsUrl"

        val i = Intent(Intent.ACTION_SEND)
            .setData(Uri.parse("smsto:"))
            .setType(resources.getString(R.string.share_intent_type))
            .putExtra("address", contact.number)
            .putExtra("sms_body", textMessage)
        startActivity(i)

    }

    private fun showUserContacts(usersContactList: ArrayList<Contact>) {
        getAdapter().updateContactList(usersContactList)
    }

    private fun getUserContacts() {
        val resolver: ContentResolver = activity!!.contentResolver
        val cursor = resolver.query(
            ContactsContract.Contacts.CONTENT_URI, null,
            null, null, null
        )

        cursor?.apply {
            if (count > 0) {
                while (moveToNext()) {
                    val id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID))
                    val name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                    val hasPhone = (cursor.getString(
                        cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)
                    )).toInt()

                    if (hasPhone > 0) {
                        val phoneCursor = resolver.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?", arrayOf(id), null
                        )

                        phoneCursor?.apply {
                            if (count > 0) {
                                while (moveToNext()) {
                                    val phoneNumber = getString(
                                        getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                                    )

                                    usersContactList.add(Contact(name, phoneNumber))
                                }
                            }
                        }
                        phoneCursor?.close()
                    }
                }
            }
        }
        cursor?.close()
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable?.clear()
        compositeDisposable = null
    }
}