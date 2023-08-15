package com.lucas.petros.usersmanagerapp.users.presentation.item.mock

import com.lucas.petros.usersmanagerapp.users.data.remote.dto.AddressDto
import com.lucas.petros.usersmanagerapp.users.data.remote.dto.CreditCardDto
import com.lucas.petros.usersmanagerapp.users.data.remote.dto.EmploymentDto
import com.lucas.petros.usersmanagerapp.users.data.remote.dto.SubscriptionDto
import com.lucas.petros.usersmanagerapp.users.data.remote.dto.UserDto

object UserMock {
    private val employment = EmploymentDto(
        title = "",
        keySkill = ""
    )

    private val address = AddressDto(
        city = "",
        streetName = "",
        streetAddress = "",
        zipCode = "",
        state = "",
        country = "",
        coordinates = null
    )

    private val creditCard = CreditCardDto(
        ccNumber = ""
    )

    private val subscription = SubscriptionDto(
        plan = "",
        status = "",
        paymentMethod = "",
        term = ""
    )

    val userMock = UserDto(
        id = 10,
        firstName = "Mockk",
        lastName = "Turbine",
        email = "mockk@mockk.com",
        avatar = "",
        gender = "Female",
        phoneNumber = "3244-1900",
        socialInsuranceNumber = "",
        dateOfBirth = "",
        employment = employment,
        address = address,
        creditCard = creditCard,
        subscription = subscription,
        uid = null,
        username = null,
        password = null
    )

    private val userNew = UserDto(
        id = 10,
        firstName = "lock",
        lastName = "Turbine",
        email = "mockk@mockk.com",
        avatar = "",
        gender = "Female",
        phoneNumber = "3244-1900",
        socialInsuranceNumber = "",
        dateOfBirth = "",
        employment = employment,
        address = address,
        creditCard = creditCard,
        subscription = subscription,
        uid = null,
        username = null,
        password = null
    )

    val listUser = listOf(userNew, userMock, userMock)
}