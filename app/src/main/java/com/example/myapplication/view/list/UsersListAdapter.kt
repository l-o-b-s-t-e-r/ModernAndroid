package com.example.myapplication.view.list

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.BR
import com.example.myapplication.R
import com.example.myapplication.domain.entities.Female
import com.example.myapplication.domain.entities.Male
import com.example.myapplication.domain.entities.User


class UsersListAdapter(private val listeners: ListViewListeners) : RecyclerView.Adapter<UsersListAdapter.ViewHolder>() {

    private inner class DiffCallback(
        private val oldUsers: List<User>,
        private val newUsers: List<User>
    ) :
        DiffUtil.Callback() {
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            oldUsers[oldItemPosition].id == newUsers[newItemPosition].id

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            oldUsers[oldItemPosition] == newUsers[newItemPosition]

        override fun getOldListSize() = oldUsers.size

        override fun getNewListSize() = newUsers.size

        override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
            return super.getChangePayload(oldItemPosition, newItemPosition)
        }
    }

    var users: MutableList<User> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            DataBindingUtil.inflate(LayoutInflater.from(parent.context), viewType, parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getObjForPosition(position))
    }

    override fun getItemCount(): Int {
        return users.size
    }

    override fun getItemViewType(position: Int): Int {
        return getLayoutIdForPosition(position)
    }

    private fun getObjForPosition(position: Int): Any {
        return users[position]
    }

    private fun getLayoutIdForPosition(position: Int): Int {
        return when (users[position].gender) {
            is Male -> R.layout.item_male
            is Female -> R.layout.item_female
        }
    }

    fun updateAll(newUsers: List<User>) {
        val diff = DiffUtil.calculateDiff(DiffCallback(users, newUsers))
        users.clear()
        users.addAll(newUsers)
        diff.dispatchUpdatesTo(this)
    }

    inner class ViewHolder(private val binding: ViewDataBinding) :
        RecyclerView.ViewHolder(binding.getRoot()) {
        fun bind(obj: Any) {
            binding.apply {
                setVariable(BR.user, obj)
                setVariable(BR.actionListener, listeners)
                executePendingBindings()
            }
        }
    }
}