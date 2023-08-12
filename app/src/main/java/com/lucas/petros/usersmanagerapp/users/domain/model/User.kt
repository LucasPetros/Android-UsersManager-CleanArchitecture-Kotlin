package com.lucas.petros.usersmanagerapp.users.domain.model

data class User(
    val id: Long,
    val firstName: String,
    val lastName: String,
    val email: String,
    val imageUrl: String,
    val gender: String,
    val phoneNumber: String,
    val dateOfBirth: String,
) : java.io.Serializable