package com.shishkin.itransition.gui.nba.uientities

data class NbaTeamUi(
    val id: Int,
    val abbreviation: String,
    val city: String,
    val conference: String,
    val division: String,
    val fullName: String,
    val name: String,
    val homeTeamScore: Int,
    val visitorTeamScore: Int
)
