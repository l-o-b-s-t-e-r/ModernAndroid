package com.example.myapplication.view.list

import android.content.Context
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
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
    private lateinit var viewModel: ListViewModel
    private val userListAdapter = UsersListAdapter()

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        val activityComponent = DaggerAppComponent.builder()
            .dataModule(DataModule())
            .build()

        activityComponent.inject(this)
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
        viewModel.apply {
            users.observe(this@ListFragment, Observer<List<User>> { users ->
                userListAdapter.addAll(users)
            })

            loadUsers()
        }

        listUsers.adapter = userListAdapter
    }

}
