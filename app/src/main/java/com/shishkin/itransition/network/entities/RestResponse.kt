package com.shishkin.itransition.network.entities

data class RestResponse<T>(
    val data: T?,
    val meta: Meta
)
