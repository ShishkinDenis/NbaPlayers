package com.shishkin.itransition.validators.rules

import java.util.*

class DateIsNotNullRule : Rule<Date?> {

    override fun isValid(obj: Date?): Boolean {
        return obj != null
    }
}