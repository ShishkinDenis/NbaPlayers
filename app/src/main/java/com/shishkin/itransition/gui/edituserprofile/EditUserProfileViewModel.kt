package com.shishkin.itransition.gui.edituserprofile

import androidx.lifecycle.ViewModel
import com.shishkin.itransition.repository.UserRepository
import javax.inject.Inject

class EditUserProfileViewModel @Inject constructor(private val userRepository: UserRepository) :
    ViewModel() {
}

