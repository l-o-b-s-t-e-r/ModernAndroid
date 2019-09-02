package com.example.myapplication.view.details

import com.example.myapplication.BR
import com.example.myapplication.R
import com.example.myapplication.databinding.DetailsFragmentBinding
import com.example.myapplication.view.base.BaseFragment


class DetailsFragment : BaseFragment<DetailsFragmentBinding, DetailsViewModel>() {

    override fun layoutId() = R.layout.details_fragment

    override fun viewModelId() = BR.viewModel

}
