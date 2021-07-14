package com.shishkin.itransition.gui.userprofile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.shishkin.itransition.repository.UserRepository
import javax.inject.Inject

class UserProfileViewModelFactory @Inject constructor(private val userRepository: UserRepository) :
    ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return UserProfileViewModel(userRepository = userRepository) as T
    }
}