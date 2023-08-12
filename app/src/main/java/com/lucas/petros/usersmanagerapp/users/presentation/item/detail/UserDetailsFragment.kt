package com.lucas.petros.usersmanagerapp.users.presentation.item.detail

import androidx.fragment.app.viewModels
import com.lucas.petros.commons.base.BaseFragment
import com.lucas.petros.usersmanagerapp.R
import com.lucas.petros.usersmanagerapp.databinding.FragmentUserDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserDetailsFragment :
    BaseFragment<FragmentUserDetailsBinding>(R.layout.fragment_user_details) {
    private val viewModel: UserDetailsViewModel by viewModels()

    override fun setupViewModel() {
        binding.vm = viewModel
    }

    override fun setupObservers() {
    }
}