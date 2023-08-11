package com.lucas.petros.usersmanagerapp.users.domain.mapper

import com.lucas.petros.commons.extension.handleOpt
import com.lucas.petros.usersmanagerapp.users.data.remote.dto.*
import com.lucas.petros.usersmanagerapp.users.domain.model.*

fun UserDto.toUser() = User(
    id = id.handleOpt(),
    firstName = firstName.handleOpt(),
    lastName = lastName.handleOpt(),
    email = email.handleOpt(),
    imageUrl = avatar.handleOpt(),
    gender = gender.handleOpt(),
    phoneNumber = phoneNumber.handleOpt(),
    socialInsuranceNumber = socialInsuranceNumber.handleOpt(),
    dateOfBirth = dateOfBirth.handleOpt(),
    employment = employment.toEmployment(),
    address = address.toAddress(),
    creditCard = creditCard.toCreditCard(),
    subscription = subscription.toSubscription()
)

fun List<UserDto>.toListUser() = map { userDto ->
    userDto.toUser()
}

fun EmploymentDto.toEmployment() = Employment(
    title = title.handleOpt(),
    keySkill = keySkill.handleOpt()
)

fun AddressDto.toAddress() = Address(
    city = city.handleOpt(),
    streetName = streetName.handleOpt(),
    streetAddress = streetAddress.handleOpt(),
    zipCode = zipCode.handleOpt(),
    state = state.handleOpt(),
    country = country.handleOpt()
)

fun CreditCardDto.toCreditCard() = CreditCard(
    ccNumber = ccNumber.handleOpt()
)

fun SubscriptionDto.toSubscription() = Subscription(
    plan = plan.handleOpt(),
    status = status.handleOpt(),
    paymentMethod = paymentMethod.handleOpt(),
    term = term.handleOpt()
)