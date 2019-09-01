package com.example.myapplication.domain.dto

import com.example.myapplication.domain.entities.Gender

data class UserDto(
    val id: String,
    val name: String,
    val gender: Gender,
    val color: Int = -1
)