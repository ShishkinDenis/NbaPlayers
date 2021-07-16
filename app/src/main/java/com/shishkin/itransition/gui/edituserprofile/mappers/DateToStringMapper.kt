package com.shishkin.itransition.gui.edituserprofile.mappers

import android.icu.text.SimpleDateFormat
import com.shishkin.itransition.gui.utils.Mapper
import java.util.*
import javax.inject.Inject

private const val DATE_FORMAT = "dd/MM/yyyy"

class DateToStringMapper @Inject constructor() : Mapper<Date, String> {

    override fun invoke(input: Date): String {
        val formatter = SimpleDateFormat(DATE_FORMAT, Locale.US)
        return formatter.format(input)
    }
}