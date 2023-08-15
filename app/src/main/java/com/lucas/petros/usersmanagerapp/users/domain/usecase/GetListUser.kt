package com.lucas.petros.usersmanagerapp.users.domain.usecase

import androidx.annotation.VisibleForTesting
import com.lucas.petros.usersmanagerapp.users.data.repository.MyRepository
import com.lucas.petros.usersmanagerapp.users.domain.mapper.toListUser
import javax.inject.Inject

class GetListUser @Inject constructor(private val repository: MyRepository) {

    suspend operator fun invoke() =
        getListUsers()

    @VisibleForTesting
    suspend fun getListUsers() = repository.getListUser()
        .toListUser()
        .sortedBy { it.firstName }
}