package com.shishkin.itransition.gui.nba.uientities

data class NbaPlayerUi(
    val id: Int,
    val firstName: String,
    val heightFeet: Int,
    val heightInches: Int,
    val lastName: String,
    val position: String,
    val team: NbaTeamUi,
    val weightPounds: Int
)
