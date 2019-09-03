package com.example.myapplication.domain.states

sealed class RefreshStatus {

    object Loading : RefreshStatus()

    object NotLoading : RefreshStatus()

}

data class RefreshState private constructor(
    val status: RefreshStatus
) {
    companion object {
        val LOADING =
            RefreshState(RefreshStatus.Loading)
        val NOT_LOADING =
            RefreshState(RefreshStatus.NotLoading)
    }
}