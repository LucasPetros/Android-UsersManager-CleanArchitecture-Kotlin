package com.lucas.petros.usersmanagerapp.users.data.remote.dto

import com.google.gson.annotations.SerializedName

data class SubscriptionDto(
    val plan: String?,
    val status: String?,

    @SerializedName("payment_method")
    val paymentMethod: String?,

    val term: String?
)