package com.lucas.petros.usersmanagerapp.users.data.remote.dto

import com.google.gson.annotations.SerializedName


data class UserDto(
    val id: Long?,
    val uid: String?,
    val password: String?,

    @SerializedName("first_name")
    val firstName: String?,

    @SerializedName("last_name")
    val lastName: String?,

    val username: String?,
    val email: String?,
    val avatar: String?,
    val gender: String?,

    @SerializedName("phone_number")
    val phoneNumber: String?,

    @SerializedName("social_insurance_number")
    val socialInsuranceNumber: String?,

    @SerializedName("date_of_birth")
    val dateOfBirth: String?,

    val employment: EmploymentDto,
    val address: AddressDto,

    @SerializedName("credit_card")
    val creditCard: CreditCardDto,

    val subscription: SubscriptionDto
)
