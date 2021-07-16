package com.shishkin.itransition.extensions

import com.shishkin.itransition.gui.edituserprofile.DatePickerConfig
import java.util.*

fun getDateAsConfig(timeStamp: Long = Date().time): DatePickerConfig {
    val timeCalendar = Calendar.getInstance().apply {
        timeInMillis = timeStamp
    }

    return DatePickerConfig(
        day = timeCalendar.get(Calendar.DAY_OF_MONTH),
        month = timeCalendar.get(Calendar.MONTH),
        year = timeCalendar.get(Calendar.YEAR)
    )
}

fun DatePickerConfig.mapToTimestamp(): Date {
    val time = Calendar.getInstance()
    time.set(Calendar.DAY_OF_MONTH, day)
    time.set(Calendar.MONTH, month)
    time.set(Calendar.YEAR, year)

    return time.time
}