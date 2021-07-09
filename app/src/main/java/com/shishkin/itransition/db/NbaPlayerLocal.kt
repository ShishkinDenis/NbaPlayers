package com.shishkin.itransition.db

import androidx.room.Entity
import androidx.room.PrimaryKey

const val PLAYER_ID_COLUMN = "nbaPlayerId"
const val PLAYERS_TABLE = "players"

@Entity(tableName = PLAYERS_TABLE)
data class NbaPlayerLocal(
    @PrimaryKey val nbaPlayerId: Int,
    val firstName: String,
    val heightFeet: Int,
    val heightInches: Int,
    val lastName: String,
    val position: String,
    val teamId: Int,
    val weightPounds: Int
)

