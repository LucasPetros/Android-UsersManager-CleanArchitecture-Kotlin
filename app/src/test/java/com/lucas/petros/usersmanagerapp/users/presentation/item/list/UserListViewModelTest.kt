package com.lucas.petros.usersmanagerapp.users.presentation.item.list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.lucas.petros.commons.base.BaseState
import com.lucas.petros.commons.data.DataResource
import com.lucas.petros.commons.extension.handleOpt
import com.lucas.petros.commons.utils.Constants
import com.lucas.petros.commons.utils.Constants.ERROR_NETWORK
import com.lucas.petros.usersmanagerapp.CoroutinesMainTestRule
import com.lucas.petros.usersmanagerapp.getOrAwaitValue
import com.lucas.petros.usersmanagerapp.users.domain.mapper.toListUser
import com.lucas.petros.usersmanagerapp.users.domain.model.User
import com.lucas.petros.usersmanagerapp.users.domain.usecase.GetListUser
import com.lucas.petros.usersmanagerapp.users.presentation.item.mock.UserMock.listUser
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class UserListViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule = CoroutinesMainTestRule()

    private var useCase = mockk<GetListUser>(relaxed = true)
    private lateinit var vm: UserListViewModel

    @Before
    fun prepare() {
        setupViewModel()
    }

    private fun setupViewModel() {
        vm = UserListViewModel(
            useCase,
        )
    }

    // Show Loading - Section
    @Test
    fun `GIVEN the initial state of ViewModel THEN showLoading is false`() {
        TestCase.assertFalse(vm.showLoading.value.handleOpt())
    }

    @Test
    fun `WHEN getUserList is called and Resource Loading is issued THEN showLoading is true`() =
        runTest {

            coEvery { useCase() } returns flow {
                emit(DataResource.Loading())
            }

            vm.getUserList()

            val value = vm.showLoading.getOrAwaitValue()
            Assert.assertEquals(true, value)
        }

    @Test
    fun `WHEN getUserList is called and Resource Success is issued THEN showLoading is false`() =
        runTest {

            coEvery { useCase() } returns flow {
                emit(DataResource.Success(listOf()))
            }

            vm.getUserList()

            val value = vm.showLoading.getOrAwaitValue()

            Assert.assertEquals(false, value)
        }

    @Test
    fun `WHEN getUserList is called and Resource Error is issued THEN showLoading is false`() =
        runTest {
            coEvery { useCase() } returns flow {
                emit(DataResource.Error("test error"))
            }

            vm.getUserList()

            val value = vm.showLoading.getOrAwaitValue()

            Assert.assertEquals(false, value)
        }

    // Show Error - Section
    @Test
    fun `GIVEN the initial state of ViewModel THEN showError is false`() {
        TestCase.assertFalse(vm.showError.value.handleOpt())
    }

    @Test
    fun `WHEN getUserList returns error THEN showError is true`() =
        runTest {
            coEvery { useCase() } returns flow {
                emit(DataResource.Error(ERROR_NETWORK))
            }

            vm.getUserList()

            val value = vm.showError.getOrAwaitValue()

            Assert.assertEquals(true, value)
        }

    // message Error - Section
    @Test
    fun `GIVEN the initial state of ViewModel THEN messageError is blank`() {
        TestCase.assertEquals(null, vm.messageError.value)
    }

    @Test
    fun `WHEN getUserList returns error THEN messageError is equals error`() =
        runTest {
            coEvery { useCase() } returns flow {
                emit(DataResource.Error(ERROR_NETWORK))
            }

            vm.getUserList()

            val value = vm.messageError.getOrAwaitValue()

            Assert.assertEquals(ERROR_NETWORK, value)
        }

    // State stateUserList - Section
    @Test
    fun `GIVEN the initial state of ViewModel THEN stateUserList is null`() {
        TestCase.assertNull(vm.stateUserList.value)
    }

    @Test
    fun `WHEN getUserList returns users THEN stateUserList_data returns users`() =
        runTest {
            val correctState = BaseState(
                isLoading = false,
                data = listUser.toListUser(),
                error = ""
            )

            coEvery { useCase() } returns flow {
                emit(DataResource.Success(listUser.toListUser()))
            }

            vm.getUserList()

            val value = vm.stateUserList.getOrAwaitValue()
            Assert.assertEquals(correctState, value)
        }

    @Test
    fun `WHEN getUserList returns IOException error THEN stateUserList_error returns network error `() =
        runTest {
            val correctState = BaseState(
                isLoading = false,
                data = null,
                error = ERROR_NETWORK
            )

            coEvery { useCase() } returns flow {
                emit(DataResource.Error(ERROR_NETWORK))
            }

            vm.getUserList()

            val value = vm.stateUserList.getOrAwaitValue()
            Assert.assertEquals(correctState, value)
        }

    @Test
    fun `WHEN getUserList returns HttpException error THEN stateUserList_error returns unexpected error `() =
        runTest {
            val correctState = BaseState(
                isLoading = false,
                data = null,
                error = Constants.UNEXPECTED_ERROR
            )

            coEvery { useCase() } returns flow {
                emit(DataResource.Error(Constants.UNEXPECTED_ERROR))
            }

            vm.getUserList()

            val value = vm.stateUserList.getOrAwaitValue()
            Assert.assertEquals(correctState, value)
        }

    @Test
    fun `WHEN getUserList returns empty list THEN stateUserList_data returns listOf`() =
        runTest {
            val correctState = BaseState(
                isLoading = false,
                data = listOf<User>(),
                error = ""
            )

            coEvery { useCase() } returns flow {
                emit(DataResource.Success(listOf()))
            }

            vm.getUserList()

            val value = vm.stateUserList.getOrAwaitValue()
            Assert.assertEquals(correctState, value)
        }

    // Search - Section
    @Test
    fun `GIVEN the initial state of UserListViewModel THEN searchValue_value is null`() {
        Assert.assertNull(vm.searchValue.value)
    }

    @Test
    fun `GIVEN the list_value has three items WHEN search_value receives a value that match just the first item THEN the list_value will have just one item`() {
        runTest {
            coEvery { useCase() } returns flow {
                emit(DataResource.Success(listUser.toListUser()))
            }

            vm.getUserList()
            val completeList = vm.userList.getOrAwaitValue()
            vm.completeList = completeList.handleOpt()
            vm.searchValue.value = "lock"
            val value = vm.list.getOrAwaitValue()
            Assert.assertEquals(1, value.size)
        }
    }

    //section
    @Test
    fun `WHEN tryAgain is instantiated THEN the GetUserListUseCase is called by the getUserList`() {
        runTest {
            vm.tryAgain()
            verify { useCase.invoke() }
        }
    }
}