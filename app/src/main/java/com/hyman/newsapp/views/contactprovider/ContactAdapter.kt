package com.hyman.newsapp.views.contactprovider

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hyman.newsapp.databinding.ContactListItemBinding
import com.hyman.newsapp.views.models.Contact

class ContactAdapter(var contactItemClick: ((Contact) -> Unit)) : RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {

    private var contacts = mutableListOf<Contact>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ContactListItemBinding.inflate(inflater, parent, false)
        return ContactViewHolder(binding)
    }

    override fun getItemCount(): Int = contacts.size

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.bind(contacts[position])
    }

    inner class ContactViewHolder(val binding: ContactListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(contact: Contact) {
            with(binding) {
                cvContactCardItem.setOnClickListener {
                    contactItemClick.invoke(contact)
                }
                model = contact
                executePendingBindings()
            }
        }
    }

    fun updateContactList(allContacts: List<Contact>) {
        contacts.clear()
        contacts.addAll(allContacts)
        notifyDataSetChanged()
    }
}