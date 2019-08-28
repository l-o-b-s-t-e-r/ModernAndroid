package com.example.myapplication.view.list

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.BR
import com.example.myapplication.R
import com.example.myapplication.domain.entities.Female
import com.example.myapplication.domain.entities.Male
import com.example.myapplication.domain.entities.UserEntity

class UsersListPagedAdapter(private val listeners: ListViewListeners) :
    PagedListAdapter<UserEntity, UsersListPagedAdapter.ViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object :
            DiffUtil.ItemCallback<UserEntity>() {
            override fun areItemsTheSame(
                oldUser: UserEntity,
                newUser: UserEntity
            ) = oldUser.id == newUser.id

            override fun areContentsTheSame(
                oldUser: UserEntity,
                newUser: UserEntity
            ) = oldUser == newUser
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.e("LIST", (currentList?.size ?: 1).toString())
        return ViewHolder(
            DataBindingUtil.inflate(LayoutInflater.from(parent.context), viewType, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position)!!)
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)!!.gender) {
            is Male -> R.layout.item_male
            is Female -> R.layout.item_female
        }
    }

    inner class ViewHolder(private val binding: ViewDataBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(obj: Any) {
            binding.apply {
                setVariable(BR.user, obj)
                setVariable(BR.actionListener, listeners)
                executePendingBindings()
            }
        }
    }
}