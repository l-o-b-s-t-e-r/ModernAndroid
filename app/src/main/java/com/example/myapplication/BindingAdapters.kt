package com.example.myapplication

import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.myapplication.domain.Loading
import com.example.myapplication.domain.RefreshState

@BindingAdapter("isVisible")
fun isVisible(view: View, isVisible: Boolean) {
    view.visibility = if (isVisible) VISIBLE else GONE
}

@BindingAdapter("refresh_state")
fun isRefreshing(view: SwipeRefreshLayout, refreshState: RefreshState?) {
    view.isRefreshing = refreshState == Loading
}