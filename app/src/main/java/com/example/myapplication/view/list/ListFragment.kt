package com.example.myapplication.view.list

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.BR
import com.example.myapplication.R
import com.example.myapplication.databinding.ListFragmentBinding
import com.example.myapplication.domain.states.DataState
import com.example.myapplication.domain.states.ErrorState
import com.example.myapplication.domain.states.NetworkState
import com.example.myapplication.view.base.BaseFragment
import kotlinx.android.synthetic.main.list_fragment.*
import javax.inject.Inject


class ListFragment : BaseFragment<ListFragmentBinding, ListViewModel>() {

    @Inject
    lateinit var viewModelFactory: ListViewModelFactory

    @Inject
    lateinit var usersListAdapter: UsersListAdapter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.viewModel = viewModel
        listUsers.adapter = usersListAdapter

        viewModel.apply {
            users.observe(this@ListFragment, Observer { users ->
                usersState.value = DataState(users)
            })

            usersState.observe(this@ListFragment, Observer { state ->
                when(state) {
                    is DataState -> {
                        usersListAdapter.submitList(state.data)
                    }
                    is ErrorState -> {
                        Log.e("E", state.data)
                    }
                    is NetworkState -> {
                        usersListAdapter.setNetworkState(state)
                    }
                }
            })

            listenUiEvents()

            listenStates()
        }

        usersListAdapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver(){
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                if (positionStart == 0 && itemCount == 1) {
                    listUsers.scrollToPosition(positionStart)
                }
            }
        })
    }

    override fun layoutId() = R.layout.list_fragment

    override fun viewModelId() = BR.viewModel
}
