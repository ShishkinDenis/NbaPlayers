package com.shishkin.itransition.gui.edituserprofile

import android.app.DatePickerDialog
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shishkin.itransition.R
import com.shishkin.itransition.repository.UserRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

private const val USER_BIRTH_DATE_FORMAT = "dd/MM/yyyy"

class EditUserProfileViewModel @Inject constructor(private val userRepository: UserRepository) :
    ViewModel() {

    val calendar: Calendar = Calendar.getInstance()

    private val _toast = MutableSharedFlow<Int>()
    val toast = _toast.asSharedFlow()

    private val _date = MutableSharedFlow<String>()
    val date = _date.asSharedFlow()

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

    private fun setUserDate() {
        val sdf = SimpleDateFormat(USER_BIRTH_DATE_FORMAT, Locale.US)
        val chosenDate = calendar.time
        val currentDate = Date()
        val chosenConvertedDate = sdf.format(chosenDate)

        if (chosenDate.before(currentDate)) {
            emitDate(chosenConvertedDate)
        } else {
            emitToastMessage(R.string.edit_user_profile_not_valid_date_toast_message)
        }
    }

    fun setDateListener(): DatePickerDialog.OnDateSetListener {
        return DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            with(calendar) {
                set(Calendar.YEAR, year)
                set(Calendar.MONTH, monthOfYear)
                set(Calendar.DAY_OF_MONTH, dayOfMonth)
            }
            setUserDate()
        }
    }
}


