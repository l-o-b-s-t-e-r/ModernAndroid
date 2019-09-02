package com.example.myapplication.view.main

import com.example.myapplication.BR
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.view.base.BaseActivity

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>()  {

    override fun layoutId() = R.layout.activity_main

    override fun viewModelId() = BR.viewModel

}
