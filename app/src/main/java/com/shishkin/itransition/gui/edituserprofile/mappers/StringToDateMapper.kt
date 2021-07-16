package com.shishkin.itransition.gui.edituserprofile.mappers

import android.icu.text.SimpleDateFormat
import com.shishkin.itransition.gui.utils.Mapper
import java.util.*

private const val DATE_FORMAT = "dd/MM/yyyy"

class StringToDateMapper : Mapper<String, Date> {

    override fun invoke(input: String): Date {
        val formatter = SimpleDateFormat(DATE_FORMAT, Locale.US)
        return formatter.parse(input)
    }
}