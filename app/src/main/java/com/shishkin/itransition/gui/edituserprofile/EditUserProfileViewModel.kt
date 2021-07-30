package com.shishkin.itransition.gui.edituserprofile

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shishkin.itransition.R
import com.shishkin.itransition.extensions.getDateAsConfig
import com.shishkin.itransition.extensions.mapToTimestamp
import com.shishkin.itransition.gui.edituserprofile.mappers.DateToStringMapper
import com.shishkin.itransition.gui.userprofile.mappers.UserUiToUserLocalMapper
import com.shishkin.itransition.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.fold
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject


interface NavigationEmitter {

    fun navigateTo(navigation: Navigation)
}

class BaseNavigationEmitter (private val fragment: Fragment) : NavigationEmitter {

    override fun navigateTo(navigation: Navigation) {
        when(navigation) {
            FinishActivityNavigation -> {
                fragment.activity?.finish()
            }
        }
    }
}

interface Navigation


object FinishActivityNavigation : Navigation


class EditUserProfileViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val dateToStringMapper: DateToStringMapper,
    private val userUiToUserLocalMapper: UserUiToUserLocalMapper
) : ViewModel() {

    private val _toast = MutableSharedFlow<Int>()
    val toast = _toast.asSharedFlow()

    private val _date = MutableSharedFlow<String>(replay = 1)
    val date = _date.asSharedFlow()

    private val _navigation = MutableSharedFlow<Navigation>()
    val navigation = _navigation.asSharedFlow()

    fun getUserDate(): DatePickerConfig {
        return if (date.replayCache.isEmpty()) {
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
        viewModelScope.launch {
            _date.emit(date)
        }
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

    fun insertUser(userUi: UserUi) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val userLocal = userUiToUserLocalMapper.invoke(userUi)
                userRepository.insertUserToDb(userLocal).collect { result ->
                    result.fold(
                      onSuccess = { id ->
                          Log.e("JEKA", "Inser user success: " + userUi)
                          withContext(Dispatchers.Main) {
                              _navigation.emit(FinishActivityNavigation)
                          }
                          // TODO: Navigation
                      },
                      onFailure = {
                          Log.e("JEKA", "Inser user error: "+it)
                          TODO()
                      }
                    )
                }
            }
        }
    }
}



