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
import com.example.myapplication.domain.Event
import com.example.myapplication.domain.EventType
import com.example.myapplication.domain.dto.UserDto
import com.example.myapplication.domain.entities.Female
import com.example.myapplication.domain.entities.Male
import com.example.myapplication.domain.states.NetworkState
import kotlinx.android.synthetic.main.item_female.view.*
import javax.inject.Inject


class UsersListAdapter @Inject constructor(private val viewModel: ListViewModel) :
    PagedListAdapter<UserDto, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

    private var networkState: NetworkState? = null

    companion object {
        private val DIFF_CALLBACK = object :
            DiffUtil.ItemCallback<UserDto>() {
            override fun areItemsTheSame(
                oldUser: UserDto,
                newUser: UserDto
            ) = oldUser.id == newUser.id

            override fun areContentsTheSame(
                oldUser: UserDto,
                newUser: UserDto
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
        fun bind(user: UserDto?) {
            binding.apply {
                setVariable(BR.user, user)
                itemView.setOnClickListener {
                    viewModel.sendEvent(Event(EventType.ITEM_CLICK, user))
                }
                itemView.icon_gender.setOnClickListener {
                    viewModel.sendEvent(Event(EventType.ICON_CLICK, user))
                }
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