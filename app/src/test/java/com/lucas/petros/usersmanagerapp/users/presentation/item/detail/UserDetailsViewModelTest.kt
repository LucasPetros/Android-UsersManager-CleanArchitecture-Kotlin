package com.lucas.petros.usersmanagerapp.users.presentation.item.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.lucas.petros.usersmanagerapp.CoroutinesMainTestRule
import com.lucas.petros.usersmanagerapp.users.domain.mapper.toUser
import com.lucas.petros.usersmanagerapp.users.presentation.item.mock.UserMock.user
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class UserDetailsViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule = CoroutinesMainTestRule()

    private lateinit var vm: UserDetailsViewModel
    private lateinit var savedStateHandle: SavedStateHandle

    @Before
    fun setup() {
        initSavedStateHandle()
        vm = UserDetailsViewModel(savedStateHandle)
    }

    fun initSavedStateHandle() {
        savedStateHandle = SavedStateHandle()
        val user = user.toUser()
        savedStateHandle["user"] = user
    }

    @Test
    fun `when ViewModel is initialized with a selected user, then retrieving user from savedStateHandle should return the selected user`() =
        runTest {
            val result = vm.args.user
            assertEquals(user.toUser(), result)
        }
}