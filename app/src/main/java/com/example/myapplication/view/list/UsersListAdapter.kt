package com.example.myapplication.view.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.BR
import com.example.myapplication.R
import com.example.myapplication.databinding.ItemFemaleBinding
import com.example.myapplication.databinding.ItemMaleBinding
import com.example.myapplication.databinding.ItemProgressBinding
import com.example.myapplication.domain.NetworkState
import com.example.myapplication.domain.entities.Female
import com.example.myapplication.domain.entities.Male
import com.example.myapplication.domain.entities.UserEntity


class UsersListAdapter(private val listeners: ListViewListeners) :
    PagedListAdapter<UserEntity, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

    private var networkState: NetworkState? = null

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.item_male -> UserViewHolder(
                ItemMaleBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            R.layout.item_female -> UserViewHolder(
                ItemFemaleBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            R.layout.item_progress -> NetworkStateViewHolder(
                ItemProgressBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            else -> throw IllegalArgumentException("unknown view type $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is UserViewHolder -> holder.bind(getItem(position))
            is NetworkStateViewHolder -> holder.bind()
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) {
            R.layout.item_progress
        } else {
            when (getItem(position)!!.gender) {
                is Male -> R.layout.item_male
                is Female -> R.layout.item_female
            }
        }
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasExtraRow()) 1 else 0
    }

    private fun hasExtraRow() = networkState != null && networkState != NetworkState.LOADED

    fun setNetworkState(newNetworkState: NetworkState?) {
        val previousState = this.networkState
        val hadExtraRow = hasExtraRow()
        this.networkState = newNetworkState
        val hasExtraRow = hasExtraRow()
        if (hadExtraRow != hasExtraRow) {
            if (hadExtraRow) {
                notifyItemRemoved(super.getItemCount())
            } else {
                notifyItemInserted(super.getItemCount())
            }
        } else if (hasExtraRow && previousState != newNetworkState) {
            notifyItemChanged(itemCount - 1)
        }
    }

    inner class UserViewHolder(private val binding: ViewDataBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: UserEntity?) {
            binding.apply {
                setVariable(BR.user, user)
                setVariable(BR.actionListener, listeners)
                executePendingBindings()
            }
        }
    }

    inner class NetworkStateViewHolder(private val binding: ItemProgressBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            binding.apply {
                executePendingBindings()
            }
        }
    }
}