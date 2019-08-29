package com.example.myapplication.view.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.domain.Loading
import com.example.myapplication.domain.RefreshState

class MainViewModel: ViewModel(), MainListeners {

    val refreshState = MutableLiveData<RefreshState>()

    override fun onRefresh() {
        refreshState.postValue(Loading)
    }

}