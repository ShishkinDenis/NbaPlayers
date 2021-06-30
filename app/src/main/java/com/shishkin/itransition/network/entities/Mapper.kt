package com.shishkin.itransition.network.entities

// TODO Evgeny почему в пакете network entities какой-то маппер непонятный?
interface Mapper<I, O> {

    fun invoke(input: I): O
}