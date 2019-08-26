package com.example.myapplication.view.list

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.myapplication.App
import com.example.myapplication.databinding.ListFragmentBinding
import com.example.myapplication.view.main.MainViewModel
import kotlinx.android.synthetic.main.list_fragment.*
import javax.inject.Inject


class ListFragment : Fragment() {

    companion object {
        fun newInstance() = ListFragment()
    }

    @Inject
    lateinit var viewModelFactory: ListViewModelFactory

    private lateinit var userListAdapter: UsersListAdapter
    private lateinit var listViewModel: ListViewModel
    private lateinit var mainViewModel: MainViewModel
    private lateinit var binding: ListFragmentBinding

    private val usersObserver = Observer<Boolean> { usersLoaded ->
        if (usersLoaded) {
            listViewModel.users.subscribe({ users ->
                userListAdapter.updateAll(users)
            }, {
                it.printStackTrace()
            })
        }
    }

    private val isLoadingObserver = Observer<Boolean> { isLoading ->
        if (isLoading.not()) {
            mainViewModel.isRefreshing.postValue(false)
        }
    }

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
        userListAdapter = UsersListAdapter(listViewModel)
        listUsers.adapter = userListAdapter

        listViewModel.apply {
            usersLoaded.observe(this@ListFragment, usersObserver)

            isLoading.observe(this@ListFragment, isLoadingObserver)

            loadAllUsers()
        }

        mainViewModel.apply {
            isRefreshing.observe(this@ListFragment, Observer<Boolean> { isRefreshing ->
                if (isRefreshing) {
                    listViewModel.loadAllUsers()
                }
            })
        }
    }
}
