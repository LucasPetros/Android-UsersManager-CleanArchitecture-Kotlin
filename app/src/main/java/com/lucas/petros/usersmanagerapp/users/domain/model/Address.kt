package com.lucas.petros.usersmanagerapp.users.domain.model

data class Address(
    val city: String,
    val streetName: String,
    val streetAddress: String,
    val zipCode: String,
    val state: String,
    val country: String,
)
