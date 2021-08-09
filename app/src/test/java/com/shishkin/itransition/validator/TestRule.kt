package com.shishkin.itransition.validator

import com.shishkin.itransition.validators.rules.Rule

class TestRule(private val boolean: Boolean) : Rule<String> {

    override fun isValid(obj: String): Boolean {
        return boolean
    }
}