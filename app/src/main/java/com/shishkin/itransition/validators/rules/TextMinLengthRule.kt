package com.shishkin.itransition.validators.rules


class TextMinLengthRule(private val min: Int) : Rule<String> {

    override fun isValid(obj: String): Boolean {
        return obj.length >= min
    }
}