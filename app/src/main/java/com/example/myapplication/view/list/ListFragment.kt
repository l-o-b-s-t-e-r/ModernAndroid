package com.example.myapplication.view.list

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.BR
import com.example.myapplication.R
import com.example.myapplication.databinding.ListFragmentBinding
import com.example.myapplication.domain.Loading
import com.example.myapplication.domain.NotLoading
import com.example.myapplication.domain.RefreshState
import com.example.myapplication.view.base.BaseFragment
import com.example.myapplication.view.main.MainViewModel
import kotlinx.android.synthetic.main.list_fragment.*
import javax.inject.Inject


class ListFragment : BaseFragment<ListFragmentBinding, ListViewModel>() {

    @Inject
    lateinit var viewModelFactory: ListViewModelFactory

    @Inject
    lateinit var usersListAdapter: UsersListAdapter

    @Inject
    lateinit var mainViewModel: MainViewModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.viewModel = viewModel
        listUsers.adapter = usersListAdapter

        viewModel.apply {
            users.observe(this@ListFragment, Observer { users ->
                usersListAdapter.submitList(users)
            })

            networkState.observe(this@ListFragment, Observer { networkState ->
                usersListAdapter.setNetworkState(networkState)
            })

            refreshState.observe(this@ListFragment, Observer { refreshState ->
                if (refreshState == NotLoading) {
                    mainViewModel.refreshState.postValue(NotLoading)
                }
            })

            startListenEvents()
        }

        mainViewModel.apply {
            refreshState.observe(this@ListFragment, Observer<RefreshState> { refreshState ->
                if (refreshState == Loading) {
                    viewModel.updateAllUsers()
                }
            })
        }

        usersListAdapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver(){
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                listUsers.scrollToPosition(positionStart)
            }
        })
    }

    override fun layoutId() = R.layout.list_fragment

    override fun viewModelId() = BR.viewModel
}
