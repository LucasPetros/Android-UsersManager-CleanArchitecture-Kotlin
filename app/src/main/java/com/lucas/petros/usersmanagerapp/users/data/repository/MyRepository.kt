package com.lucas.petros.usersmanagerapp.users.data.repository

import com.lucas.petros.usersmanagerapp.users.data.remote.dto.UserDto

interface MyRepository {

    suspend fun getListUser(): List<UserDto>

}