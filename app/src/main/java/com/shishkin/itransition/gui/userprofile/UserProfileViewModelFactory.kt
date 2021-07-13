package com.shishkin.itransition.gui.userprofile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.shishkin.itransition.repository.NbaRepository
import javax.inject.Inject

class UserProfileViewModelFactory @Inject constructor(private val nbaRepository: NbaRepository) :
    ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return UserProfileViewModel(nbaRepository = nbaRepository) as T
    }
}