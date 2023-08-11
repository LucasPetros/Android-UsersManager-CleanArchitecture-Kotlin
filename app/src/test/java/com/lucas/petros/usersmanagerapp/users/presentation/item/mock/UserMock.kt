package com.lucas.petros.usersmanagerapp.users.presentation.item.mock

import com.lucas.petros.usersmanagerapp.users.domain.model.*

object UserMock {
    private val employment = Employment(
        title = "",
        keySkill = ""
    )

    private val address = Address(
        city = "",
        streetName = "",
        streetAddress = "",
        zipCode = "",
        state = "",
        country = ""
    )

    private val creditCard = CreditCard(
        ccNumber = ""
    )

    private val subscription = Subscription(
        plan = "",
        status = "",
        paymentMethod = "",
        term = ""
    )

    val user = User(
        id = 10,
        firstName = "Mockk",
        lastName = "Turbine",
        email = "mockk@mockk.com",
        imageUrl = "",
        gender = "Female",
        phoneNumber = "3244-1900",
        socialInsuranceNumber = "",
        dateOfBirth = "",
        employment = employment,
        address = address,
        creditCard = creditCard,
        subscription = subscription
    )

    val listUser = listOf(user, user, user)
}