package com.shishkin.itransition.gui.edituserprofile

import android.icu.text.SimpleDateFormat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shishkin.itransition.R
import com.shishkin.itransition.extensions.getDateAsConfig
import com.shishkin.itransition.extensions.mapToTimestamp
import com.shishkin.itransition.repository.UserRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

private const val USER_BIRTH_DATE_FORMAT = "dd/MM/yyyy"

class EditUserProfileViewModel @Inject constructor(private val userRepository: UserRepository) :
    ViewModel() {

    private val _toast = MutableSharedFlow<Int>()
    val toast = _toast.asSharedFlow()

    private val _date = MutableSharedFlow<String>(replay = 1)
    val date = _date.asSharedFlow()

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
        val sdf = SimpleDateFormat(USER_BIRTH_DATE_FORMAT, Locale.US)

        val chosenDate = config.mapToTimestamp()

        val currentDate = Date()
        val chosenConvertedDate = sdf.format(chosenDate)

        if (chosenDate.before(currentDate)) {
            emitDate(chosenConvertedDate)
        } else {
            emitToastMessage(R.string.edit_user_profile_not_valid_date_toast_message)
        }
    }
}



