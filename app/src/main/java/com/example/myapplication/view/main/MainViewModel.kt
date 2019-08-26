package com.example.myapplication.view.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel(), MainListeners {

    val isRefreshing = MutableLiveData<Boolean>().apply { postValue(false) }

    override fun onRefresh() {
        isRefreshing.postValue( true)
    }

}