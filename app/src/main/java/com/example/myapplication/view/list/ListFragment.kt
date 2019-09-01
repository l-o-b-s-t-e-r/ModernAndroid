package com.example.myapplication.view.list

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.myapplication.databinding.ListFragmentBinding
import com.example.myapplication.domain.Loading
import com.example.myapplication.domain.NotLoading
import com.example.myapplication.domain.RefreshState
import com.example.myapplication.view.main.MainViewModel
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.list_fragment.*
import javax.inject.Inject


class ListFragment : Fragment() {

    @Inject
    lateinit var listViewModel: ListViewModel

    @Inject
    lateinit var viewModelFactory: ListViewModelFactory

    @Inject
    lateinit var usersListAdapter: UsersListAdapter

    @Inject
    lateinit var mainViewModel: MainViewModel

    lateinit var binding: ListFragmentBinding

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ListFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.viewModel = listViewModel
        listUsers.adapter = usersListAdapter

        listViewModel.apply {
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
                    listViewModel.updateAllUsers()
                }
            })
        }
    }
}
