package com.example.myapplication.view.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.BR
import com.example.myapplication.R
import com.example.myapplication.domain.entities.Female
import com.example.myapplication.domain.entities.Male
import com.example.myapplication.domain.entities.User


class UsersListAdapter : RecyclerView.Adapter<UsersListAdapter.ViewHolder>() {

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

    fun addAll(newUsers: List<User>) {
        users.addAll(newUsers)
        notifyItemRangeChanged(users.size, newUsers.size)
    }

    class ViewHolder(private val binding: ViewDataBinding) :
        RecyclerView.ViewHolder(binding.getRoot()) {
        fun bind(obj: Any) {
            binding.apply {
                setVariable(BR.user, obj)
                executePendingBindings()
            }
        }
    }
}