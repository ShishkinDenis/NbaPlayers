package com.shishkin.itransition.gui.edituserprofile

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shishkin.itransition.R
import com.shishkin.itransition.extensions.getDateAsConfig
import com.shishkin.itransition.extensions.mapToTimestamp
import com.shishkin.itransition.gui.edituserprofile.mappers.DateToStringMapper
import com.shishkin.itransition.gui.userprofile.mappers.UserLocalToUserUiMapper
import com.shishkin.itransition.gui.userprofile.mappers.UserUiToUserLocalMapper
import com.shishkin.itransition.navigation.FinishActivityNavigation
import com.shishkin.itransition.navigation.Navigation
import com.shishkin.itransition.network.entities.ResultState
import com.shishkin.itransition.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.util.*
import javax.inject.Inject

private const val EDIT_USER_PROFILE_VIEW_MODEL_TAG = "EditUserProfileViewModel"
private const val USER_INSERTION_ERROR = "User insertion error: %s"
private const val USER_ID = 1

class EditUserProfileViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val dateToStringMapper: DateToStringMapper,
    private val userUiToUserLocalMapper: UserUiToUserLocalMapper,
    private val userLocalToUserUiMapper: UserLocalToUserUiMapper
) : ViewModel() {

    private val _toast = MutableSharedFlow<Int>()
    val toast = _toast.asSharedFlow()

    private val _navigation = MutableSharedFlow<Navigation>()
    val navigation = _navigation.asSharedFlow()

    private val _userState: MutableStateFlow<UserUi> = MutableStateFlow(getEmptyUser())
    val userState: StateFlow<UserUi> = _userState

    // TODO Добавить прогресс

    //    TODO реализовать валидацию согласно требованиям
/*    val isApplyButtonEnabled: Flow<Boolean> =
        combine(_userName, _userBirthDate) { userName, userBirthDate ->
            val isUserNameCorrect = userName.length > 3
            val userBirthDateCorrect = userBirthDate.length > 3
            return@combine isUserNameCorrect and userBirthDateCorrect
        }*/

    init {
        loadUser()
    }

    private fun getEmptyUser() : UserUi = UserUi(
      id = 0,
      name = "",
      birthDate = "",
      profileImageUri = null
    )

    fun setUserName(name: String) {
       _userState.tryEmit(
         _userState.value.copy(
           name = name
         )
       )
    }

    fun setUserBirthDate(userBirthDate: String) {
        _userState.tryEmit(
          _userState.value.copy(
            birthDate = userBirthDate
          )
        )
    }

    fun setProfileImageUri(uri: Uri) {
        _userState.tryEmit(
          _userState.value.copy(
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
            _toast.emit(toastMessage)
        }
    }

    private fun emitDate(date: String) {
        _userState.tryEmit(
          _userState.value.copy(
            birthDate = date
          )
        )
    }

    fun setUserDate(
        config: DatePickerConfig
    ) {
        val chosenDate = config.mapToTimestamp()
        val currentDate = Date()
        val chosenConvertedDate = dateToStringMapper.invoke(chosenDate)

        if (chosenDate.before(currentDate)) {
            emitDate(chosenConvertedDate)
        } else {
            emitToastMessage(R.string.edit_user_profile_not_valid_date_toast_message)
        }
    }

    fun insertUser() {
        viewModelScope.launch (Dispatchers.IO) {
                val userLocal = userUiToUserLocalMapper.invoke(_userState.value)
                userRepository.insertUserToDb(userLocal).collect { result ->
                    result.fold(
                        onSuccess = {
                            withContext(Dispatchers.Main) {
                                _navigation.emit(FinishActivityNavigation)
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
        viewModelScope.launch (Dispatchers.IO) {
                userRepository.getUserFromDb()
                    .collect { result ->
                        result.fold(
                            onSuccess = { userLocal ->
                                withContext(Dispatchers.Main) {
                                    val mapped = userLocalToUserUiMapper.invoke(userLocal)
                                    _userState.value = mapped
                                }
                            },
                            onFailure = { error ->
                                // do nothing
                            }
                        )
                    }
        }
    }
}




