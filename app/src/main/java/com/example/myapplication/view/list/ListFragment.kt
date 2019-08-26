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
import kotlinx.android.synthetic.main.list_fragment.*
import javax.inject.Inject


class ListFragment : Fragment() {

    companion object {
        fun newInstance() = ListFragment()
    }

    @Inject
    lateinit var viewModelFactory: ListViewModelFactory

    private lateinit var userListAdapter: UsersListAdapter
    private lateinit var viewModel: ListViewModel
    private lateinit var binding: ListFragmentBinding

    private val usersObserver = Observer<Boolean> { usersLoaded ->
        if (usersLoaded) {
            viewModel.users.subscribe({ users ->
                userListAdapter.updateAll(users)
            }, {
                it.printStackTrace()
            })
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
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ListViewModel::class.java)

        binding.viewModel = viewModel
        userListAdapter = UsersListAdapter(viewModel)
        listUsers.adapter = userListAdapter

        viewModel.apply {
            usersLoaded.observe(this@ListFragment, usersObserver)

            loadAllUsers()
        }
    }
}
