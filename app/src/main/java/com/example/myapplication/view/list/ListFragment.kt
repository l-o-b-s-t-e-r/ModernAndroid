package com.example.myapplication.view.list

import android.content.Context
import android.graphics.Color
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.example.myapplication.App
import com.example.myapplication.R
import com.example.myapplication.domain.entities.User
import kotlinx.android.synthetic.main.list_fragment.*
import com.example.myapplication.di.DaggerAppComponent
import com.example.myapplication.di.DataModule
import javax.inject.Inject


class ListFragment : Fragment() {

    companion object {
        fun newInstance() = ListFragment()
    }

    @Inject
    lateinit var viewModelFactory: ListViewModelFactory
    private lateinit var userListAdapter: UsersListAdapter
    private lateinit var viewModel: ListViewModel

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        App.appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.list_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ListViewModel::class.java)

        userListAdapter = UsersListAdapter(viewModel)
        listUsers.adapter = userListAdapter

        viewModel.apply {
            users.observe(this@ListFragment, Observer<List<User>> { users ->
                userListAdapter.updateAll(users)
            })

            loadUsers()
        }
    }
}
