package com.shishkin.itransition.validators.rules

interface Rule<T> {

    fun isValid(obj: T): Boolean
}