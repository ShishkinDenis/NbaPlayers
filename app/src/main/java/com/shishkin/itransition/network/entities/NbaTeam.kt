package com.shishkin.itransition.network.entities

data class NbaTeam(
//    TODO fix camel case
    val id: Int,
    val abbreviation: String,
    val city: String,
    val conference: String,
    val division: String,
    val full_name: String,
    val name: String
)
