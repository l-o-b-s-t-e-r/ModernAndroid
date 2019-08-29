package com.example.myapplication.view.list

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.myapplication.App
import com.example.myapplication.databinding.ListFragmentBinding
import com.example.myapplication.domain.Loading
import com.example.myapplication.domain.NotLoading
import com.example.myapplication.domain.RefreshState
import com.example.myapplication.view.main.MainViewModel
import kotlinx.android.synthetic.main.list_fragment.*
import javax.inject.Inject


class ListFragment : Fragment() {

    companion object {
        fun newInstance() = ListFragment()
    }

    @Inject
    lateinit var viewModelFactory: ListViewModelFactory

    private lateinit var usersListAdapter: UsersListAdapter
    private lateinit var listViewModel: ListViewModel
    private lateinit var mainViewModel: MainViewModel
    private lateinit var binding: ListFragmentBinding

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        App.appComponent.inject(this)
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
        listViewModel = ViewModelProviders.of(this, viewModelFactory)[ListViewModel::class.java]
        mainViewModel = ViewModelProviders.of(activity!!)[MainViewModel::class.java]

        binding.viewModel = listViewModel
        usersListAdapter = UsersListAdapter(listViewModel)
        listUsers.adapter = usersListAdapter

        listViewModel.apply {
           users.observe(this@ListFragment, Observer { users ->
                Log.e("ListFragment", "users size: ${users.size}")
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
