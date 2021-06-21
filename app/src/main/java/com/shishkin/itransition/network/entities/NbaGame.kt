package com.shishkin.itransition.network.entities

//TODO naming-camel case
data class NbaGame(
    val id: Int,
    val date: String,
    val home_team: NbaTeam,
    val home_team_score: Int,
    val period: Int,
    val postseason: Boolean,
    val season: Int,
    val status: String,
    val time: String,
    val visitor_team: NbaTeam,
    val visitor_team_score: Int
)


