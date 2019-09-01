package com.example.myapplication.domain

import com.example.myapplication.domain.dto.UserDto
import com.example.myapplication.domain.entities.UserEntity
import javax.inject.Inject

class UserMapper @Inject constructor() {

    fun toEntity(userDto: UserDto): UserEntity{
        userDto.apply {
            return UserEntity(id, name, gender, color)
        }
    }

    fun toDto(userEntity: UserEntity): UserDto {
        userEntity.apply {
            return UserDto(id, name, gender, color)
        }
    }
}