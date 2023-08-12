package com.lucas.petros.usersmanagerapp.users.presentation.item.detail

import androidx.lifecycle.SavedStateHandle
import com.lucas.petros.commons.base.BaseViewModel
import com.lucas.petros.usersmanagerapp.users.domain.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserDetailsViewModel @Inject constructor(
    state: SavedStateHandle
) : BaseViewModel() {

    val args = UserDetailsFragmentArgs.fromSavedStateHandle(state)
    var user: User? = args.user
}
