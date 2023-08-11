package com.lucas.petros.usersmanagerapp.users.presentation.item.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.lucas.petros.usersmanagerapp.R
import com.lucas.petros.usersmanagerapp.databinding.UserItemBinding
import com.lucas.petros.usersmanagerapp.users.domain.model.User

internal class UserListAdapter(
    private val onItemClick: (User) -> Unit,
) : ListAdapter<User, UserListAdapter.DataHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataHolder {
        return DataHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.user_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: DataHolder, position: Int) {
        holder.binding.run {
            val userItem = getItem(position)
            item = userItem
            root.setOnClickListener { onItemClick(userItem) }
        }
    }

    inner class DataHolder(val binding: UserItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    private object DiffCallback : DiffUtil.ItemCallback<User>() {

        override fun areItemsTheSame(
            oldItem: User,
            newItem: User
        ) = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: User,
            newItem: User
        ) = oldItem == newItem
    }

}