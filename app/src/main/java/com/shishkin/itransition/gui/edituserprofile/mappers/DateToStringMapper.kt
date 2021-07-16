package com.shishkin.itransition.gui.edituserprofile.mappers

import android.icu.text.SimpleDateFormat
import com.shishkin.itransition.gui.utils.Mapper
import java.util.*

private const val DATE_FORMAT = "dd/MM/yyyy"

class DateToStringMapper : Mapper<Date, String> {

    override fun invoke(input: Date): String {
        val sdf = SimpleDateFormat(DATE_FORMAT, Locale.US)
        return sdf.format(input)
    }
}