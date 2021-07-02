package com.shishkin.itransition.gui.utils


interface Mapper<I, O> {

    fun invoke(input: I): O
}