package com.shishkin.itransition.network.entities

import androidx.room.Embedded
import androidx.room.Relation

data class TeamWithPlayers(
    @Embedded val team: NbaTeam,
    @Relation(
        parentColumn = "team",
        entityColumn = "id"
    )
    val players: List<NbaPlayer>
)