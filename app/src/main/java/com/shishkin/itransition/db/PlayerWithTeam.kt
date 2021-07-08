package com.shishkin.itransition.db

import androidx.room.Embedded
import androidx.room.Relation

data class PlayerWithTeam(
    @Embedded val team: NbaTeamLocal,
    @Relation(
        parentColumn = "team",
        entityColumn = "id"
    )
    val players: List<NbaPlayerLocal>
)
