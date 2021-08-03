package com.shishkin.itransition.gui.edituserprofile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.shishkin.itransition.gui.edituserprofile.mappers.DateToStringMapper
import com.shishkin.itransition.gui.userprofile.mappers.UserLocalToUserUiMapper
import com.shishkin.itransition.gui.userprofile.mappers.UserUiToUserLocalMapper
import com.shishkin.itransition.repository.UserRepository
import javax.inject.Inject

class EditUserProfileViewModelFactory @Inject constructor(
    private val userRepository: UserRepository,
    private val dateToStringMapper: DateToStringMapper,
    private val userUiToUserLocalMapper: UserUiToUserLocalMapper,
    private val userLocalToUserUiMapper: UserLocalToUserUiMapper
) :
    ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return EditUserProfileViewModel(
            userRepository = userRepository,
            dateToStringMapper = dateToStringMapper,
            userUiToUserLocalMapper = userUiToUserLocalMapper,
            userLocalToUserUiMapper = userLocalToUserUiMapper
        ) as T
    }
}
