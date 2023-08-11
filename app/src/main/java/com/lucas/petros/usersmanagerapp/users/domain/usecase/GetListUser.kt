package com.lucas.petros.usersmanagerapp.users.domain.usecase

import com.lucas.petros.commons.utils.Constants.ERROR_NETWORK
import com.lucas.petros.commons.utils.Constants.UNEXPECTED_ERROR
import com.lucas.petros.commons.data.DataResource
import com.lucas.petros.network.errorMessage
import com.lucas.petros.usersmanagerapp.users.data.repository.MyRepository
import com.lucas.petros.usersmanagerapp.users.domain.mapper.toListUser
import com.lucas.petros.usersmanagerapp.users.domain.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetListUser @Inject constructor(private val repository: MyRepository) {

    operator fun invoke(): Flow<DataResource<List<User>>> = flow {
        try {
            emit(DataResource.Loading())
            val response = repository.getListUser().toListUser()
            emit(DataResource.Success(response))
        } catch (e: HttpException) {
            emit(DataResource.Error(e.errorMessage() ?: UNEXPECTED_ERROR))
        } catch (e: IOException) {
            emit(DataResource.Error(ERROR_NETWORK))
        }
    }
}