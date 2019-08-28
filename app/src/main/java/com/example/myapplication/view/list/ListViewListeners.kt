package com.example.myapplication.view.list

import com.example.myapplication.domain.entities.UserEntity

interface ListViewListeners {

    fun onItemClick(user: UserEntity)

}