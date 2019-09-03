package com.example.myapplication.domain.states

import androidx.paging.PagedList
import com.example.myapplication.domain.dto.UserDto

sealed class UsersState

sealed class NetworkStatus {

    object Running : NetworkStatus()

    object Success : NetworkStatus()

    object Failed : NetworkStatus()

}

data class DataState(val data: PagedList<UserDto>) : UsersState()

data class ErrorState(val data: String) : UsersState()

@Suppress("DataClassPrivateConstructor")
data class NetworkState private constructor(
    val status: NetworkStatus,
    val msg: String? = null
) : UsersState() {
    companion object {
        val LOADED = NetworkState(NetworkStatus.Success)
        val LOADING = NetworkState(NetworkStatus.Running)

        fun error(msg: String?) = NetworkState(
            NetworkStatus.Failed,
            msg
        )
    }
}