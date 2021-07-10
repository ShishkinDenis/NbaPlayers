package com.shishkin.itransition.db

import androidx.room.Embedded
import androidx.room.Relation

private const val NBA_PLAYER_LOCAL_TEAM_ID = "teamId"
private const val NBA_TEAM_LOCAL_ID = "id"

data class PlayerWithTeam(
    @Embedded val player: NbaPlayerLocal,
    @Relation(
        parentColumn = NBA_PLAYER_LOCAL_TEAM_ID,
        entityColumn = NBA_TEAM_LOCAL_ID
    )
    val team: NbaTeamLocal
)
