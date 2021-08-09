package com.shishkin.itransition.validator.rules

import com.google.common.truth.Truth.assertThat
import com.shishkin.itransition.validators.Validator
import com.shishkin.itransition.validators.rules.Rule
import com.shishkin.itransition.validators.rules.TextMinLengthRule
import org.junit.Before
import org.junit.Test

private const val MINIMUM_CHARACTERS_AMOUNT = 4

class TextMinLengthRuleTest {

    private lateinit var validator: Validator<String>

    @Before
    fun setUp() {
        validator = Validator()
        val rules: Set<Rule<String>> = setOf(TextMinLengthRule(MINIMUM_CHARACTERS_AMOUNT))
        validator.addRules(rules)
    }

    @Test
    fun inputEqualsMinimumCharactersAmountReturnsTrue() {
        val input = "1aБ!"
        assertThat(validator.validate(input)).isTrue()
    }

    @Test
    fun inputUnderMinimumCharactersAmountReturnsFalse() {
        val input = "yz"
        assertThat(validator.validate(input)).isFalse()
    }

    @Test
    fun inputGreaterMinimumCharactersAmountReturnsTrue() {
        val input = "John Doe"
        assertThat(validator.validate(input)).isTrue()
    }
}