package com.shishkin.itransition.validators

import com.shishkin.itransition.validators.rules.Rule

class Validator<T> {

    var rules: List<Rule<T>> = emptyList()

    fun addRules(rules: List<Rule<T>>) {
        this.rules = rules
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
