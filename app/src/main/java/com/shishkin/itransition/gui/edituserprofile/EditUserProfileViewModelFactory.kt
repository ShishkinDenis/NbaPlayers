package com.shishkin.itransition.gui.edituserprofile

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.shishkin.itransition.di.BirthDateValidator
import com.shishkin.itransition.di.CoroutineContextProvider
import com.shishkin.itransition.di.ImageUriValidator
import com.shishkin.itransition.di.UserNameValidator
import com.shishkin.itransition.gui.edituserprofile.mappers.DateToStringMapper
import com.shishkin.itransition.gui.edituserprofile.mappers.StringToDateMapper
import com.shishkin.itransition.gui.userprofile.mappers.UserLocalToUserUiMapper
import com.shishkin.itransition.gui.userprofile.mappers.UserUiToUserLocalMapper
import com.shishkin.itransition.repository.UserRepository
import com.shishkin.itransition.validators.Validator
import java.util.*
import javax.inject.Inject

class EditUserProfileViewModelFactory @Inject constructor(
    private val userRepository: UserRepository,
    private val dateToStringMapper: DateToStringMapper,
    private val userUiToUserLocalMapper: UserUiToUserLocalMapper,
    private val userLocalToUserUiMapper: UserLocalToUserUiMapper,
    @UserNameValidator private val userNameValidator: Validator<String>,
    @BirthDateValidator private val birthDateValidator: Validator<Date?>,
    @ImageUriValidator private val imageUriValidator: Validator<Uri?>,
    private val stringToDateMapper: StringToDateMapper,
    private val contextProvider: CoroutineContextProvider
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return EditUserProfileViewModel(
            userRepository = userRepository,
            dateToStringMapper = dateToStringMapper,
            userUiToUserLocalMapper = userUiToUserLocalMapper,
            userLocalToUserUiMapper = userLocalToUserUiMapper,
            userNameValidator = userNameValidator,
            birthDateValidator = birthDateValidator,
            imageUriValidator = imageUriValidator,
            stringToDateMapper = stringToDateMapper,
            contextProvider = contextProvider
        ) as T
    }
}
