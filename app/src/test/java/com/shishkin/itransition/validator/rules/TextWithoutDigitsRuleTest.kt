package com.shishkin.itransition.validator.rules

import com.google.common.truth.Truth
import com.shishkin.itransition.validators.Validator
import com.shishkin.itransition.validators.rules.Rule
import com.shishkin.itransition.validators.rules.TextWithoutDigitsRule
import org.junit.Before
import org.junit.Test

class TextWithoutDigitsRuleTest {

    lateinit var validator: Validator<String>

    @Before
    fun setUp() {
        validator = Validator()
        val rules: Set<Rule<String>> = setOf(TextWithoutDigitsRule())
        validator.addRules(rules)
    }

    @Test
    fun inputWithoutDigitsReturnsTrue() {
        val input = "abc_эюя"
        Truth.assertThat(validator.validate(input)).isTrue()
    }

    @Test
    fun inputWithDigitsReturnsFalse() {
        val input = "John2000"
        Truth.assertThat(validator.validate(input)).isFalse()
    }
}