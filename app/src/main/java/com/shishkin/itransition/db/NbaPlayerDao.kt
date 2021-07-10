package com.shishkin.itransition.db

import androidx.room.*

@Dao
interface NbaPlayerDao {

    @Transaction
    @Query("SELECT * FROM $PLAYERS_TABLE")
    suspend fun getPlayersWithTeams(): List<PlayerWithTeam>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllPlayers(nbaPlayers: List<NbaPlayerLocal>): List<Long>

    @Query("DELETE FROM $PLAYERS_TABLE")
    suspend fun clearAllPlayers(): Int

    @Query("SELECT * FROM $PLAYERS_TABLE WHERE $PLAYER_ID_COLUMN = :playerId")
    fun getSpecificPlayer(playerId: Int?): PlayerWithTeam

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllTeams(nbaTeams: List<NbaTeamLocal>): List<Long>
}