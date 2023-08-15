package com.lucas.petros.usersmanagerapp.users.domain.usecase

import com.lucas.petros.usersmanagerapp.users.data.repository.MyRepository
import com.lucas.petros.usersmanagerapp.users.domain.mapper.toListUser
import com.lucas.petros.usersmanagerapp.users.domain.model.User
import com.lucas.petros.usersmanagerapp.users.presentation.item.mock.UserMock.listUser
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.spyk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class GetUserListTest {

    private val repository: MyRepository = mockk(relaxed = true)
    private lateinit var useCase: GetListUser

    @Before
    fun prepare() {
        useCase = GetListUser(repository)
    }

    // Get User List - Section
    @Test
    fun `WHEN repository_getListUser returns and empty list THEN getListUsers() returns an empty list`() =
        runBlocking {
            coEvery { repository.getListUser() } returns emptyList()
            assertEquals(listOf<User>(), useCase.getListUsers())
        }

    @Test
    fun `WHEN repository_getListUser returns a list with three item THEN getListUsers() returns the mapped and sorted list`() =
        runBlocking {
            coEvery { repository.getListUser() } returns listUser
            assertEquals(
                listUser.toListUser().sortedBy { it.firstName },
                useCase.getListUsers()
            )
        }

    // Invoke
    @Test
    fun `WHEN calling the useCase THEN useCase returns the value returned from getListUsers()`() =
        runBlocking {
            val mockedUseCase = spyk(useCase)
            coEvery { mockedUseCase.getListUsers() } returns listUser.toListUser()
            assertEquals(
                listUser.toListUser(),
                mockedUseCase()
            )
        }
}