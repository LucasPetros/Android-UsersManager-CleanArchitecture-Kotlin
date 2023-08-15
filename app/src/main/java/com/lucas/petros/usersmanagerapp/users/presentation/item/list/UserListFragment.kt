package com.lucas.petros.usersmanagerapp.users.presentation.item.list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.lucas.petros.commons.base.BaseFragment
import com.lucas.petros.usersmanagerapp.R
import com.lucas.petros.usersmanagerapp.databinding.FragmentUserListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserListFragment : BaseFragment<FragmentUserListBinding>(R.layout.fragment_user_list) {

    private val vm: UserListViewModel by viewModels()

    private val userListAdapter = UserListAdapter { user ->
        val direction = UserListFragmentDirections.toUserDetailsFragment(user)
        findNavController().navigate(direction)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }

    override fun setupViewModel() {
        binding.vm = vm
        lifecycle.addObserver(vm)
    }

    override fun setupObservers() {
        observeList()
    }

    private fun setupRecyclerView() {
        binding.rvUsers.adapter = userListAdapter
    }

    private fun observeList() {
        vm.list.observe(viewLifecycleOwner) { list ->
            userListAdapter.submitList(list)
            binding.rvUsers.postDelayed({
                binding.rvUsers.scrollToPosition(0)
            }, 200)
        }
    }
}