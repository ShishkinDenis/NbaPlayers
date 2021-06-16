package com.shishkin.itransition.network.entities


data class NbaPlayer(
//    TODO fix camel case
    val id: Int,
    val first_name: String,
    val height_feet: Int,
    val height_inches: Int,
    val last_name: String,
    val position: String,
    val team: NbaTeam,
    val weight_pounds: Int
)


