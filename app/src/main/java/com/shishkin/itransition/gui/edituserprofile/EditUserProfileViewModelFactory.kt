package com.shishkin.itransition.gui.edituserprofile

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.shishkin.itransition.db.UserLocal
import com.shishkin.itransition.di.CoroutineContextProvider
import com.shishkin.itransition.di.ProfileBirthDateValidator
import com.shishkin.itransition.di.ProfileImageUriValidator
import com.shishkin.itransition.di.ProfileUserNameValidator
import com.shishkin.itransition.gui.edituserprofile.mappers.DateToStringMapper
import com.shishkin.itransition.gui.edituserprofile.mappers.StringToDateMapper
import com.shishkin.itransition.gui.userprofile.mappers.UserUiToUserLocalMapper
import com.shishkin.itransition.gui.utils.Mapper
import com.shishkin.itransition.repository.UserRepository
import com.shishkin.itransition.validators.Validator
import java.util.*
import javax.inject.Inject

class EditUserProfileViewModelFactory @Inject constructor(
    private val userRepository: UserRepository,
    private val dateToStringMapper: DateToStringMapper,
    private val userUiToUserLocalMapper: UserUiToUserLocalMapper,
    private val userLocalToUserUiMapper: Mapper<UserLocal, UserUi>,
    @ProfileUserNameValidator private val userNameValidator: Validator<String>,
    @ProfileBirthDateValidator private val birthDateValidator: Validator<Date?>,
    @ProfileImageUriValidator private val imageUriValidator: Validator<Uri?>,
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
