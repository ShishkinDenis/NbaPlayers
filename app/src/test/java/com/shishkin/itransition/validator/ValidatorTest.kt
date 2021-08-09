package com.shishkin.itransition.validator

import com.google.common.truth.Truth
import com.shishkin.itransition.validators.Validator
import com.shishkin.itransition.validators.rules.Rule
import org.junit.Before
import org.junit.Test

class ValidatorTest {

    lateinit var validator: Validator<String>

    @Before
    fun setUp() {
        validator = Validator()
    }

    @Test
    fun ifAnyRuleIsFalseValidateFunctionReturnFalse() {
        val rules: Set<Rule<String>> = setOf(TestRule(true), TestRule(false))
        validator.addRules(rules)
        val input = "abc"
        Truth.assertThat(validator.validate(input)).isFalse()
    }

    @Test
    fun ifAllRulesAreTrueValidateFunctionReturnTrue() {
        val rules: Set<Rule<String>> = setOf(TestRule(true), TestRule(true))
        validator.addRules(rules)
        val input = "abc"
        Truth.assertThat(validator.validate(input)).isTrue()
    }
}

