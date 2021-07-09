package com.shishkin.itransition.db

import androidx.room.Entity
import androidx.room.PrimaryKey

const val TEAM = "team"

@Entity(tableName = TEAM)
data class NbaTeamLocal(
    @PrimaryKey val id: Int,
    val abbreviation: String,
    val city: String,
    val conference: String,
    val division: String,
    val fullName: String,
    val name: String,
    val homeTeamScore: Int,
    val visitorTeamScore: Int
)
