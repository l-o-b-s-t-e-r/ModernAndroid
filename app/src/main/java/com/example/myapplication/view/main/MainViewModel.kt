package com.example.myapplication.view.main

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.domain.states.RefreshState
import com.example.myapplication.domain.usecases.GetRefreshStateUseCase
import io.reactivex.android.schedulers.AndroidSchedulers

class MainViewModel(private val getRefreshStateUseCase: GetRefreshStateUseCase): ViewModel(), MainListeners {

    private val refreshStatePublishSubject = getRefreshStateUseCase.execute()

    val refreshState = MutableLiveData<RefreshState>()

    override fun onRefresh() {
        refreshStatePublishSubject.onNext(RefreshState.LOADING)
    }

    @SuppressLint("CheckResult")
    fun listenStates() {
        refreshStatePublishSubject
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { refreshState.value = it }
    }
}