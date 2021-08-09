package com.shishkin.itransition

import android.net.Uri
import com.google.common.truth.Truth
import com.shishkin.itransition.validators.Validator
import com.shishkin.itransition.validators.rules.Rule
import com.shishkin.itransition.validators.rules.UriIsNotNullRule
import org.junit.Before
import org.junit.Test

class UriIsNotNullRuleTest {

    lateinit var validator: Validator<Uri?>

    @Before
    fun setUp() {
        validator = Validator()
        val rules: Set<Rule<Uri?>> = setOf(UriIsNotNullRule())
        validator.addRules(rules)
    }

    @Test
    fun ifUriIsNullReturnsFalse() {
        val input = null
        Truth.assertThat(validator.validate(input)).isFalse()
    }

    @Test
    fun ifUriNotNullReturnsTrue() {
        val input = Uri.parse("uri")
        Truth.assertThat(validator.validate(input)).isTrue()
    }
}