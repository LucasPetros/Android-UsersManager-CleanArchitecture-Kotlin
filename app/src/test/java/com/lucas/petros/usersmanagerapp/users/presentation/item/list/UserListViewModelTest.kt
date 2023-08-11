package com.lucas.petros.usersmanagerapp.users.presentation.item.list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.lucas.petros.commons.utils.Constants
import com.lucas.petros.commons.data.DataResource
import com.lucas.petros.commons.extension.handleOpt
import com.lucas.petros.usersmanagerapp.CoroutinesMainTestRule
import com.lucas.petros.usersmanagerapp.getOrAwaitValue
import com.lucas.petros.usersmanagerapp.users.domain.usecase.GetListUser
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.lang.reflect.Method

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

    private fun invokePrivateFunction(): Any? {
        val method: Method = vm.javaClass.getDeclaredMethod("getUserList")
        method.isAccessible = true
        return method.invoke(vm)
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

            invokePrivateFunction()

            val value = vm.showLoading.getOrAwaitValue()
            Assert.assertEquals(true, value)
        }

    @Test
    fun `WHEN getUserList is called and Resource Success is issued THEN showLoading is false`() =
        runTest {

            coEvery { useCase() } returns flow {
                emit(DataResource.Success(listOf()))
            }

            invokePrivateFunction()

            val value = vm.showLoading.getOrAwaitValue()

            Assert.assertEquals(false, value)
        }

    @Test
    fun `WHEN getUserList is called and Resource Error is issued THEN showLoading is false`() =
        runTest {
            coEvery { useCase() } returns flow {
                emit(DataResource.Error("test error"))
            }

            invokePrivateFunction()

            val value = vm.showLoading.getOrAwaitValue()

            Assert.assertEquals(false, value)
        }

    // Show Error - Section

    @Test
    fun `GIVEN the initial state of ViewModel THEN showInternetErrorDialog is false`() {
        TestCase.assertFalse(vm.showError.value.handleOpt())
    }

    @Test
    fun `WHEN getUserList returns error THEN showError is true`() =
        runTest {
            coEvery { useCase( ) } returns flow {
                emit(DataResource.Error(Constants.ERROR_NETWORK))
            }

            invokePrivateFunction()

            val value = vm.showError.getOrAwaitValue()

            Assert.assertEquals(true, value)
        }
}