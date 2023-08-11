package com.lucas.petros.usersmanagerapp.users.data.remote.dto

import com.google.gson.annotations.SerializedName


data class EmploymentDto(
    val title: String?,

    @SerializedName("key_skill")
    val keySkill: String?
)
