package com.example.myapplication.domain

sealed class Status

object Running : Status()

object Success: Status()

object Failed: Status()

@Suppress("DataClassPrivateConstructor")
data class NetworkState private constructor(
    val status: Status,
    val msg: String? = null
) {
    companion object {
        val LOADED =
            NetworkState(Success)
        val LOADING =
            NetworkState(Running)
        fun error(msg: String?) = NetworkState(
            Failed,
            msg
        )
    }
}