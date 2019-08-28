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
    private lateinit var userListPagedAdapter: UsersListPagedAdapter
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

    private val usersPagedObserver = Observer<Boolean> { usersLoaded ->
        if (usersLoaded) {
            Log.e("TAG", "usersLoaded")
            listViewModel.usersPaged.subscribe({ users ->
                Log.e("NEW", users.size.toString())
                userListPagedAdapter.submitList(null)
                userListPagedAdapter.submitList(users)
            }, {
                it.printStackTrace()
            })
        }
    }

    private val usersPagedByNameObserver = Observer<Boolean> { usersLoaded ->
        if (usersLoaded) {
            Log.e("TAG", "usersLoaded")
            listViewModel.usersPagedByName.observe(this, Observer { users ->
                Log.e("NEW", users.size.toString())
                userListPagedAdapter.submitList(users)
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
        userListPagedAdapter = UsersListPagedAdapter(listViewModel)
        //listUsers.adapter = userListAdapter
        listUsers.adapter = userListPagedAdapter

        listViewModel.apply {
            //usersLoaded.observe(this@ListFragment, usersObserver)
            //usersLoaded.observe(this@ListFragment, usersPagedObserver)
            usersLoaded.observe(this@ListFragment, usersPagedByNameObserver)

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
