package com.lucas.petros.usersmanagerapp.users.presentation.item.list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.Observer
import com.lucas.petros.usersmanagerapp.CoroutinesMainTestRule
import com.lucas.petros.usersmanagerapp.users.domain.mapper.toListUser
import com.lucas.petros.usersmanagerapp.users.domain.model.User
import com.lucas.petros.usersmanagerapp.users.domain.usecase.GetListUser
import com.lucas.petros.usersmanagerapp.users.presentation.item.mock.UserMock.listUser
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
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
    private val searchValueObserver = Observer<String> {}
    private val resourceList = Observer<List<User>?> {}
    private val listObserver = Observer<List<User>> {}

    @Before
    fun prepare() {
        setupViewModel()
        prepareObservers()
    }

    private fun setupViewModel() {
        vm = UserListViewModel(
            useCase,
        )
    }

    private fun prepareObservers() {
        vm.searchValue.observeForever(searchValueObserver)
        vm.resourceList.data.observeForever(resourceList)
        vm.list.observeForever(listObserver)

    }

    @After
    fun cleanUp() {
        cleanUpObservers()
    }

    private fun cleanUpObservers() {
        vm.searchValue.removeObserver(searchValueObserver)
        vm.resourceList.data.removeObserver(resourceList)
        vm.list.removeObserver(listObserver)
    }

    // Resource - Section
    @Test
    fun `GIVEN the initial state of UserListViewModel THEN resourceList_value is null`() {
        Assert.assertNull(vm.resourceList.data.value)
    }

    @Test
    fun `WHEN resourceList_value is null THEN list_value is null`() {
        vm.run { Assert.assertNull(list.value) }
    }

    @Test
    fun `WHEN calling onCreate() THEN the GetListUsersUseCase is called by the resourceList`() {
        vm.run {
            val lifecycleOwner = mockk<LifecycleOwner>()
            onCreate(lifecycleOwner)
            verify { vm.resourceList.loadData() }
        }
    }

    @Test
    fun `WHEN calling onCreate() THEN the list_value and completedList are equal to the resource_list_data`() {
        vm.run {
            coEvery { useCase() } returns listUser.toListUser()
            val lifecycleOwner = mockk<LifecycleOwner>()
            onCreate(lifecycleOwner)
            assertEquals(listUser.toListUser(), list.value)
            assertEquals(listUser.toListUser(), completeList)
        }
    }

    @Test
    fun `GIVEN the list_value has three items WHEN search_value receives a value that match just the first item THEN the list_value will have just one item`() {
        vm.run {
            coEvery { useCase() } returns listUser.toListUser()
            val lifecycleOwner = mockk<LifecycleOwner>()
            onCreate(lifecycleOwner)
            searchValue.value = "lock"
            Assert.assertEquals(1, list.value?.size)
        }
    }

    @Test
    fun `WHEN calling tryAgain() THEN the GetUserListUseCase is called by the resourceList`() {
        vm.run {
            tryAgain()
            verify { resourceList.loadData() }
        }
    }

    @Test
    fun `Verify if UserListViewModel extends DefaultLifeCycleObserver`() {
        val mockedVm = mockk<UserListViewModel>(relaxUnitFun = true)

        val lifecycleOwner = mockk<LifecycleOwner>()
        val lifecycle = LifecycleRegistry(mockk())
        every { lifecycleOwner.lifecycle } returns lifecycle

        lifecycle.addObserver(mockedVm)
        verify(exactly = 0) { mockedVm.onCreate(any()) }

        lifecycle.currentState = Lifecycle.State.CREATED
        verify { mockedVm.onCreate(any()) }
    }
}