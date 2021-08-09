package com.shishkin.itransition

import com.google.common.truth.Truth
import com.shishkin.itransition.gui.edituserprofile.mappers.StringToDateMapper
import com.shishkin.itransition.validators.Validator
import com.shishkin.itransition.validators.rules.ChosenDateBeforeCurrentDateRule
import com.shishkin.itransition.validators.rules.Rule
import org.junit.Before
import org.junit.Test
import java.util.*

private const val FUTURE_DATE = "01/01/2051"
private const val PAST_DATE = "01/01/2001"

class ChosenDateBeforeCurrentDateRuleTest {

    private lateinit var validator: Validator<Date?>

    @Before
    fun setUp() {
        validator = Validator()
        val rules: Set<Rule<Date?>> = setOf(ChosenDateBeforeCurrentDateRule())
        validator.addRules(rules)
    }

    @Test
    fun ifInputDateIsBeforeCurrentDateReturnsTrue() {
        val input = StringToDateMapper().invoke(PAST_DATE)
        Truth.assertThat(validator.validate(input)).isTrue()
    }

    @Test
    fun ifInputDateIsAfterCurrentDateReturnsFalse() {
        val input = StringToDateMapper().invoke(FUTURE_DATE)
        Truth.assertThat(validator.validate(input)).isFalse()
    }

    @Test
    fun ifInputDateIsEqualCurrentDateReturnsFalse() {
        val input = Date()
        Truth.assertThat(validator.validate(input)).isFalse()
    }
}
