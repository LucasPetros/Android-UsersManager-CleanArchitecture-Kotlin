package com.lucas.petros.usersmanagerapp.users.domain.usecase

import app.cash.turbine.test
import com.lucas.petros.commons.data.DataResource
import com.lucas.petros.commons.utils.Constants.ERROR_NETWORK
import com.lucas.petros.commons.utils.Constants.UNEXPECTED_ERROR
import com.lucas.petros.usersmanagerapp.users.data.repository.MyRepository
import com.lucas.petros.usersmanagerapp.users.domain.mapper.toListUser
import com.lucas.petros.usersmanagerapp.users.domain.model.User
import com.lucas.petros.usersmanagerapp.users.presentation.item.mock.UserMock.listUser
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

@ExperimentalCoroutinesApi
class GetUserListTest {

    private val repository: MyRepository = mockk(relaxed = true)
    private val useCase = GetListUser(repository)

    //Resource loading - section
    @Test
    fun `WHEN getListUser() THEN return resource loading and repository_getListUser()`() =
        runTest {
            val loading = DataResource.Loading<List<User>>()
            coEvery { repository.getListUser() }
            useCase().test {
                Assert.assertEquals(
                    loading.data,
                    awaitItem().data
                )
                coVerify { repository.getListUser() }
                cancelAndIgnoreRemainingEvents()
            }
        }

    //Resource success - section
    @Test
    fun `WHEN result is success THEN getListUser() return resource success with user list`() =
        runTest {
            val success = DataResource.Success(listUser.toListUser())
            coEvery { repository.getListUser() } returns listUser
            useCase()
                .test {
                    awaitItem()
                    Assert.assertEquals(
                        success.data,
                        awaitItem().data
                    )
                    cancelAndIgnoreRemainingEvents()
                }
            coVerify { repository.getListUser() }
        }

    @Test
    fun `WHEN result is success THEN getListUser() return resource success with empty list`() =
        runTest {
            val success = DataResource.Success<List<User>>(listOf())
            coEvery { repository.getListUser() } returns listOf()
            useCase()
                .test {
                    awaitItem()
                    Assert.assertEquals(
                        success.data,
                        awaitItem().data
                    )
                    cancelAndIgnoreRemainingEvents()
                }
            coVerify { repository.getListUser() }
        }

    //Resource error - section
    @Test
    fun `WHEN getListUser() returns HttpException THEN return Resource error with message Unexpected error`() =
        runTest {
            val unexpectedError = DataResource.Error<List<User>>(UNEXPECTED_ERROR)
            val response = Response.error<Any>(404, "".toResponseBody(null))
            coEvery { repository.getListUser() } throws HttpException(response)
            useCase()
                .test {
                    awaitItem()
                    Assert.assertEquals(
                        unexpectedError.message,
                        awaitItem().message
                    )
                    cancelAndIgnoreRemainingEvents()
                }
            coVerify { repository.getListUser() }
        }

    @Test
    fun `WHEN getListUser() returns IOException THEN return resource error with message network error`() =
        runTest {
            val networkError = DataResource.Error<List<User>>(ERROR_NETWORK)
            coEvery { repository.getListUser() } throws IOException()
            useCase()
                .test {
                    awaitItem()
                    Assert.assertEquals(
                        networkError.message,
                        awaitItem().message
                    )
                    cancelAndIgnoreRemainingEvents()
                }
            coVerify { repository.getListUser() }
        }
}