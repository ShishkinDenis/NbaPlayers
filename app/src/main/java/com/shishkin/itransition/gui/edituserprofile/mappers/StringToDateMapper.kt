package com.shishkin.itransition.gui.edituserprofile.mappers

import com.shishkin.itransition.gui.utils.Mapper
import java.util.*

private const val DATE_FORMAT = "dd/MM/yyyy"

class StringToDateMapper : Mapper<String, Date> {

    override fun invoke(input: String): Date {
        val sdf = android.icu.text.SimpleDateFormat(DATE_FORMAT, Locale.US)
        return sdf.parse(input)
    }
}