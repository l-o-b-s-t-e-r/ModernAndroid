package com.example.myapplication.view.main

import android.os.Bundle
import com.example.myapplication.BR
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.view.base.BaseActivity

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>()  {

    override fun layoutId() = R.layout.activity_main

    override fun viewModelId() = BR.viewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.listenStates()
    }
}
