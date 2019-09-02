package com.example.myapplication.view.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

abstract class BaseActivity<B : ViewDataBinding, V : ViewModel> : AppCompatActivity(), HasAndroidInjector {

    @Inject
    lateinit var viewModel: V

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    lateinit var binding: B

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutId())
        binding.lifecycleOwner = this
        binding.setVariable(viewModelId(), viewModel)
    }

    override fun androidInjector(): AndroidInjector<Any> {
        return androidInjector
    }

    abstract fun layoutId(): Int

    abstract fun viewModelId(): Int
}