package com.shishkin.itransition.gui.userprofile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.shishkin.itransition.di.CoroutineContextProvider
import com.shishkin.itransition.gui.userprofile.mappers.UserLocalToUserUiMapper
import com.shishkin.itransition.repository.UserRepository
import javax.inject.Inject

class UserProfileViewModelFactory @Inject constructor(
    private val userRepository: UserRepository,
    private val userLocalToUserUiMapper: UserLocalToUserUiMapper,
    private val contextProvider: CoroutineContextProvider
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return UserProfileViewModel(
            userRepository = userRepository,
            userLocalToUserUiMapper = userLocalToUserUiMapper,
            contextProvider = contextProvider
        ) as T
    }
}