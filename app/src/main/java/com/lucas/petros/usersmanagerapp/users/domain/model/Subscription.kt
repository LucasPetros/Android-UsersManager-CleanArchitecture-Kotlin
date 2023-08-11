package com.lucas.petros.usersmanagerapp.users.domain.model

data class Subscription(
    val plan: String,
    val status: String,
    val paymentMethod: String,
    val term: String
)