package com.lucas.petros.usersmanagerapp.users.presentation.item.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.lucas.petros.usersmanagerapp.CoroutinesMainTestRule
import com.lucas.petros.usersmanagerapp.users.domain.mapper.toUser
import com.lucas.petros.usersmanagerapp.users.presentation.item.mock.UserMock.userMock
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
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

    private fun initSavedStateHandle() {
        savedStateHandle = SavedStateHandle()
        val user = userMock.toUser()
        savedStateHandle["user"] = user
    }

    @Test
    fun `when ViewModel is initialized with a selected user, then retrieving user from savedStateHandle should return the selected user`() =
        vm.run {
            assertEquals(userMock.toUser(), args.user)
        }
}