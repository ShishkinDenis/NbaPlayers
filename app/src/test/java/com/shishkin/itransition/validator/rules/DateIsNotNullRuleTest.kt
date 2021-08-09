package com.shishkin.itransition.validator.rules

import com.google.common.truth.Truth
import com.shishkin.itransition.validators.Validator
import com.shishkin.itransition.validators.rules.DateIsNotNullRule
import com.shishkin.itransition.validators.rules.Rule
import org.junit.Before
import org.junit.Test
import java.util.*

class DateIsNotNullRuleTest {

    private lateinit var validator: Validator<Date?>

    @Before
    fun setUp() {
        validator = Validator()
        val rules: Set<Rule<Date?>> = setOf(DateIsNotNullRule())
        validator.addRules(rules)
    }

    @Test
    fun ifDateIsNullReturnsFalse() {
        val input = null
        Truth.assertThat(validator.validate(input)).isFalse()
    }

    @Test
    fun ifDateNotNullReturnsTrue() {
        val input = Date()
        Truth.assertThat(validator.validate(input)).isTrue()
    }
}