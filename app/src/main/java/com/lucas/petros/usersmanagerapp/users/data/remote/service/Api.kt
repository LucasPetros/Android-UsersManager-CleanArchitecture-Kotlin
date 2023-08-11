package com.lucas.petros.usersmanagerapp.users.data.remote.service

import com.lucas.petros.usersmanagerapp.users.data.remote.dto.UserDto
import retrofit2.http.GET

interface Api {
    @GET("/api/v2/users?size=100")
    suspend fun getListUsers(): List<UserDto>
}