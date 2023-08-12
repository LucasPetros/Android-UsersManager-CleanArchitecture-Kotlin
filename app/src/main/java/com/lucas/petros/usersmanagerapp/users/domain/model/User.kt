package com.lucas.petros.usersmanagerapp.users.domain.model

data class User(
    val id: Long,
    val firstName: String,
    val lastName: String,
    val email: String,
    val imageUrl: String,
    val gender: String,
    val phoneNumber: String,
    val socialInsuranceNumber: String,
    val dateOfBirth: String,
    val employment: Employment,
    val address: Address,
    val creditCard: CreditCard,
    val subscription: Subscription
) : java.io.Serializable