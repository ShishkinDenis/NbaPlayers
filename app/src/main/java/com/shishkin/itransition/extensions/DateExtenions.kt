package com.shishkin.itransition.extensions

import com.shishkin.itransition.gui.edituserprofile.DatePickerConfig
import java.util.Calendar
import java.util.Date

fun getDateAsConfig(timeStamp: Long = Date().time): DatePickerConfig {
  val timeCalendear = Calendar.getInstance().apply {
    timeInMillis = timeStamp
  }

  return DatePickerConfig(
    day = timeCalendear.get(Calendar.DAY_OF_MONTH),
    month = timeCalendear.get(Calendar.MONTH),
    year = timeCalendear.get(Calendar.YEAR)
  )
}

fun DatePickerConfig.mapToTimestamp(): Date {
  val time = Calendar.getInstance()
  time.set(Calendar.DAY_OF_MONTH, day)
  time.set(Calendar.MONTH, month)
  time.set(Calendar.YEAR, year)

  return time.time
}