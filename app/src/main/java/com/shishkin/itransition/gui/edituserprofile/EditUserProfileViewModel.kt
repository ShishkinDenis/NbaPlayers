package com.shishkin.itransition.gui.edituserprofile

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shishkin.itransition.R
import com.shishkin.itransition.di.BirthDateValidator
import com.shishkin.itransition.di.CoroutineContextProvider
import com.shishkin.itransition.di.ImageUriValidator
import com.shishkin.itransition.di.UserNameValidator
import com.shishkin.itransition.extensions.getDateAsConfig
import com.shishkin.itransition.extensions.mapToTimestamp
import com.shishkin.itransition.gui.edituserprofile.mappers.DateToStringMapper
import com.shishkin.itransition.gui.edituserprofile.mappers.StringToDateMapper
import com.shishkin.itransition.gui.userprofile.mappers.UserLocalToUserUiMapper
import com.shishkin.itransition.gui.userprofile.mappers.UserUiToUserLocalMapper
import com.shishkin.itransition.navigation.FinishActivityNavigation
import com.shishkin.itransition.navigation.Navigation
import com.shishkin.itransition.repository.UserRepository
import com.shishkin.itransition.validators.Validator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.util.*
import javax.inject.Inject

private const val EDIT_USER_PROFILE_VIEW_MODEL_TAG = "EditUserProfileViewModel"
private const val USER_INSERTION_ERROR = "User insertion error: %s"

class EditUserProfileViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val dateToStringMapper: DateToStringMapper,
    private val userUiToUserLocalMapper: UserUiToUserLocalMapper,
    private val userLocalToUserUiMapper: UserLocalToUserUiMapper,
    @UserNameValidator private val userNameValidator: Validator<String>,
    @BirthDateValidator private val birthDateValidator: Validator<Date?>,
    @ImageUriValidator private val imageUriValidator: Validator<Uri?>,
    private val stringToDateMapper: StringToDateMapper,
    private val contextProvider: CoroutineContextProvider
) : ViewModel() {

    private val toastData = MutableSharedFlow<Int>()
    val toast = toastData.asSharedFlow()

    private val navigationData = MutableSharedFlow<Navigation>()
    val navigation = navigationData.asSharedFlow()

    private val userStateData: MutableStateFlow<UserUi> = MutableStateFlow(getEmptyUser())
    val userState: StateFlow<UserUi> = userStateData

    private val progressData = MutableStateFlow(false)
    val progress = progressData.asStateFlow()

    private val userNameErrorData = MutableStateFlow(false)
    val userNameError = userNameErrorData.asStateFlow()

    private val userBirthDateErrorData = MutableStateFlow(false)
    val userBirthDateError = userBirthDateErrorData.asStateFlow()

    private val userImageUriErrorData = MutableStateFlow(false)
    val userImageUriError = userImageUriErrorData.asStateFlow()

    val applyButton: Flow<Boolean> =
        combine(
            userNameErrorData,
            userBirthDateErrorData,
            userImageUriErrorData
        ) { userName, userBirthDate, userImageError ->
            return@combine userName and userBirthDate and userImageError
        }

    init {
        loadUser()
        setErrorIfInvalid()
    }

    private fun setErrorIfInvalid() {
        viewModelScope.launch(contextProvider.io) {
            userStateData.collect { userUi ->
                userNameErrorData.value = userNameValidator.validate(userUi.name)
                userBirthDateErrorData.value =
                    birthDateValidator.validate(stringToDateMapper.invoke(userUi.birthDate))
                userImageUriErrorData.value = imageUriValidator.validate(userUi.profileImageUri)
            }
        }
    }

    private fun getEmptyUser(): UserUi = UserUi(
        id = 0,
        name = "",
        birthDate = "",
        profileImageUri = null
    )

    fun setUserName(name: String) {
        userStateData.tryEmit(
            userStateData.value.copy(
                name = name
            )
        )
    }

    fun setUserBirthDate(userBirthDate: String) {
        userStateData.tryEmit(
            userStateData.value.copy(
                birthDate = userBirthDate
            )
        )
    }

    fun setProfileImageUri(uri: Uri?) {
        userStateData.tryEmit(
            userStateData.value.copy(
                profileImageUri = uri
            )
        )
    }

    fun getUserDate(): DatePickerConfig {
        return if (userState.value.birthDate.isEmpty()) {
            getDateAsConfig()
        } else {
            val time = Date().time
            getDateAsConfig(time)
        }
    }

    private fun emitToastMessage(toastMessage: Int) {
        viewModelScope.launch {
            toastData.emit(toastMessage)
        }
    }

    private fun emitDate(date: String) {
        userStateData.tryEmit(
            userStateData.value.copy(
                birthDate = date
            )
        )
    }

    fun setUserDate(
        config: DatePickerConfig
    ) {
        val chosenDate = config.mapToTimestamp()
        val chosenConvertedDate = dateToStringMapper.invoke(chosenDate)
        if (birthDateValidator.validate(chosenDate)) {
            emitDate(chosenConvertedDate)
        } else {
            emitToastMessage(R.string.edit_user_profile_not_valid_date_toast_message)
        }
    }

    fun insertUser() {
        viewModelScope.launch(contextProvider.io) {
            val userLocal = userUiToUserLocalMapper.invoke(userStateData.value)
            userRepository.insertUserToDb(userLocal).collect { result ->
                result.fold(
                    onSuccess = {
                        withContext(Dispatchers.Main) {
                            navigationData.emit(FinishActivityNavigation)
                        }
                    },
                    onFailure = {
                        Timber.tag(EDIT_USER_PROFILE_VIEW_MODEL_TAG).e(USER_INSERTION_ERROR, it)
                    }
                )
            }
        }
    }

    private fun loadUser() {
        viewModelScope.launch(contextProvider.io) {
            progressData.value = true
            delay(1000L)
            userRepository.getUserFromDb()
                .collect { result ->
                    result.fold(
                        onSuccess = { userLocal ->
                            withContext(Dispatchers.Main) {
                                val userUi = userLocalToUserUiMapper.invoke(userLocal)
                                userStateData.value = userUi
                                progressData.value = false
                            }
                        },
                        onFailure = {
                            progressData.value = false
                        }
                    )
                }
        }
    }
}




