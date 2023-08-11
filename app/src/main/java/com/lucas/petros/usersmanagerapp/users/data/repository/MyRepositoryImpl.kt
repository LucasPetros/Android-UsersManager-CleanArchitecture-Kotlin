package com.lucas.petros.usersmanagerapp.users.data.repository

import com.lucas.petros.usersmanagerapp.users.data.remote.dto.UserDto
import com.lucas.petros.usersmanagerapp.users.data.remote.service.Api
import javax.inject.Inject

class MyRepositoryImpl @Inject constructor(private val api: Api) : MyRepository {
    override suspend fun getListUser(): List<UserDto> = api.getListUsers()

}