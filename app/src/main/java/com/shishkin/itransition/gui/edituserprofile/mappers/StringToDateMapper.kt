package com.shishkin.itransition.gui.edituserprofile.mappers

import android.icu.text.SimpleDateFormat
import com.shishkin.itransition.gui.utils.Mapper
import java.util.*
import javax.inject.Inject

private const val DATE_FORMAT = "dd/MM/yyyy"

class StringToDateMapper @Inject constructor() : Mapper<String?, Date?> {

    override fun invoke(input: String?): Date? {
        val formatter = SimpleDateFormat(DATE_FORMAT, Locale.US)
        return if (input?.isNotEmpty() == true) {
            formatter.parse(input)
        } else {
            null
        }
    }
}