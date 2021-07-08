package com.shishkin.itransition.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface NbaPlayerDao {

    @Query("SELECT * FROM $PLAYERS")
    fun getAllPlayers(): List<NbaPlayerLocal>
//    fun getAllPlayers(): List<PlayerWithTeam>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllPlayers(nbaPlayers: List<NbaPlayerLocal>): List<Long>

    @Query("DELETE FROM $PLAYERS")
    suspend fun clearAllPlayers(): Int

    @Query("SELECT * FROM $PLAYERS WHERE $ID = :playerId")
    fun getSpecificPlayer(playerId: Int?): NbaPlayerLocal
}