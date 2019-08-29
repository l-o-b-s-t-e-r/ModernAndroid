package com.example.myapplication.domain

sealed class RefreshState

object Loading : RefreshState()

object NotLoading: RefreshState()