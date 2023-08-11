package com.lucas.petros.usersmanagerapp.users.data.remote.dto

import com.google.gson.annotations.SerializedName

data class CreditCardDto(
    @SerializedName("cc_number")
    val ccNumber: String?
)
