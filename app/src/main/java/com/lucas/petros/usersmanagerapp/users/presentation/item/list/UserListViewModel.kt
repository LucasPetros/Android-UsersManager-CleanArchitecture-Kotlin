package com.lucas.petros.usersmanagerapp.users.presentation.item.list

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.lucas.petros.commons.base.BaseViewModel
import com.lucas.petros.commons.data.DataResourceV2
import com.lucas.petros.commons.data.resource
import com.lucas.petros.usersmanagerapp.users.domain.model.User
import com.lucas.petros.usersmanagerapp.users.domain.usecase.GetListUser
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(
    private val useCase: GetListUser
) : BaseViewModel(), DefaultLifecycleObserver {

    val searchValue = MutableLiveData<String>()

    val resourceList: DataResourceV2<List<User>> = resource(viewModelScope) {
        useCase()
    }

    @VisibleForTesting
    lateinit var completeList: List<User>
    val list = MediatorLiveData<List<User>>().apply {
        addSource(searchValue) { searchValue ->
            value = completeList.filter {
                it.firstName.contains(searchValue, ignoreCase = true)
            }
        }

        addSource(resourceList.data) { list ->
            list?.let {
                completeList = list
                value = list
            }
        }
    }

    fun tryAgain() {
        resourceList.loadData()
    }

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        resourceList.loadData()
    }
}