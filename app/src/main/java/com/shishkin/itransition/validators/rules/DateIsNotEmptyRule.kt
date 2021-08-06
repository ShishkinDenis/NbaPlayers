package com.shishkin.itransition.validators.rules

import java.util.*

class DateIsNotEmptyRule : Rule<Date?> {

    override fun isValid(obj: Date?): Boolean {
        return obj != null
    }
}