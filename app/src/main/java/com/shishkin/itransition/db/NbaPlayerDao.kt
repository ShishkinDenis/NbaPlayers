package com.shishkin.itransition.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.shishkin.itransition.network.entities.NbaPlayer

@Dao
interface NbaPlayerDao {

    @Query("SELECT * FROM players")
    fun getAllPlayers(): List<NbaPlayer>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllPlayers(nbaPlayers: List<NbaPlayer>)

    @Query("DELETE FROM players")
    suspend fun clearAllPlayers()

    @Query("SELECT * FROM players WHERE id = :playerId")
    fun getSpecificPlayer(playerId: Int?): NbaPlayer

}