package com.shishkin.itransition.network.entities

interface Mapper<I, O> {

    fun invoke(input: I): O
}