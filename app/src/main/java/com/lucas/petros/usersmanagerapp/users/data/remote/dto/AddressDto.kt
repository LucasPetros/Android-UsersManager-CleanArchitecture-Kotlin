package com.lucas.petros.usersmanagerapp.users.data.remote.dto

import com.google.gson.annotations.SerializedName

data class AddressDto(
    val city: String?,

    @SerializedName("street_name")
    val streetName: String?,

    @SerializedName("street_address")
    val streetAddress: String?,

    @SerializedName("zip_code")
    val zipCode: String?,

    val state: String?,
    val country: String?,
    val coordinates: CoordinatesDto?
)
