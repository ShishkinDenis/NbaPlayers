package com.shishkin.itransition.validators

import com.shishkin.itransition.validators.rules.Rule

class Validator<T> {

    private val rules: MutableSet<Rule<T>> = mutableSetOf()

    fun addRules(rules: Set<Rule<T>>) {
        rules.forEach { rule ->
            this.rules.add(rule)
        }
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
