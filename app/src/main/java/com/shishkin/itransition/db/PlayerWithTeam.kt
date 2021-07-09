package com.shishkin.itransition.db

import androidx.room.Embedded
import androidx.room.Relation


data class PlayerWithTeam(
    @Embedded val player: NbaPlayerLocal,
    @Relation(
        parentColumn = "teamId",
        entityColumn = "id"
    )
    val team: NbaTeamLocal
)
