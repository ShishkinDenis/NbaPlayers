package com.shishkin.itransition.validators.rules

class TextWithoutDigitsRule : Rule<String> {

    override fun isValid(obj: String): Boolean {
        val regexPatternAnyCharsButDigits = "^([^0-9]*)\$".toRegex()
        return regexPatternAnyCharsButDigits.matches(obj)
    }
}