package com.shishkin.itransition.network.entities

data class Meta(
//    TODO Fix camel case
    val total_pages: Int,
    val current_page: Int,
    val next_page: Int,
    val per_page: Int,
    val total_count: Int
)


