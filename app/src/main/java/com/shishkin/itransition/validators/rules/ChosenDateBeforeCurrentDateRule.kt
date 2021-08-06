package com.shishkin.itransition.validators.rules

import java.util.*

class ChosenDateBeforeCurrentDateRule : Rule<Date?> {

    override fun isValid(obj: Date?): Boolean {
        val currentDate = Date()
        return obj?.before(currentDate) == true
    }
}