package com.example.myapplication.view.list

import com.example.myapplication.domain.entities.User

interface ListViewListeners {

    fun onItemClick(user: User)

}