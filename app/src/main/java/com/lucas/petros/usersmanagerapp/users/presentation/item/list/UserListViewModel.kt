package com.lucas.petros.usersmanagerapp.users.presentation.item.list

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.lucas.petros.commons.base.BaseState
import com.lucas.petros.commons.base.BaseViewModel
import com.lucas.petros.commons.utils.ResourceUtil
import com.lucas.petros.usersmanagerapp.users.domain.model.User
import com.lucas.petros.usersmanagerapp.users.domain.usecase.GetListUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(
    private val useCase: GetListUser
) : BaseViewModel() {

    val stateUserList = MutableLiveData<BaseState<List<User>>>()
    val userList = stateUserList.map { it.data }
    val showLoading = stateUserList.map { it.isLoading }
    val messageError = stateUserList.map { it.error }
    val showError = stateUserList.map { it.error.isNotBlank() }

    val searchValue = MutableLiveData<String>()
    var completeList: List<User>? = null
    val list = MediatorLiveData<List<User>>().apply {
        addSource(searchValue) { searchValue ->
            value = completeList?.filter {
                it.firstName.contains(searchValue,ignoreCase = true)
            }
        }

        addSource(userList) { list ->
            list?.let {
                completeList = list
                value = list
            }
        }
    }

    fun tryAgain() {
        getUserList()
    }

    fun getUserList() {
        useCase.invoke().onEach { result ->
            ResourceUtil.mapResultToState(result, stateUserList)
        }.launchIn(viewModelScope)
    }
}