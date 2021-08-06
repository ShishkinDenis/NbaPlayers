package com.shishkin.itransition.validators

import com.shishkin.itransition.validators.rules.Rule

class Validator<T> {

    lateinit var rules: List<Rule<T>>

    fun addRules(rules: List<Rule<T>>): Validator<T> {
        this.rules = rules
        return this
    }

    fun validate(text: T): Boolean {
        var isValid = true
        for (rule in rules) {
            if (!rule.isValid(text)) {
                isValid = false
                break
            }
        }
        return isValid
    }
}
