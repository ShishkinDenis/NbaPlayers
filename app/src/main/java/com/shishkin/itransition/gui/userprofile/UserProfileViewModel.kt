package com.shishkin.itransition.gui.userprofile

import androidx.lifecycle.ViewModel
import com.shishkin.itransition.repository.NbaRepository
import javax.inject.Inject

class UserProfileViewModel @Inject constructor(private val nbaRepository: NbaRepository) :
    ViewModel() {
}