package com.shishkin.itransition.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.shishkin.itransition.network.entities.ID
import com.shishkin.itransition.network.entities.NbaPlayer
import com.shishkin.itransition.network.entities.PLAYERS

@Dao
interface NbaPlayerDao {

    @Query("SELECT * FROM $PLAYERS")
    fun getAllPlayers(): List<NbaPlayer>
//    fun getAllPlayers(): List<TeamWithPlayers>

    // TODO Evgeny: Когда ты вставляешь данные из БД, оно тебе возвращает список ID. Как правильно,
    // лучше его добавлять и тут, т.е:
    /*
    fun insertAllPlayers(nbaPlayers: List<NbaPlayer>) : List<Long>
     */

    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    fun insertAllPlayers(nbaPlayers: List<NbaPlayer>)
    fun insertAllPlayers(nbaPlayers: List<NbaPlayer>): List<Long>

    // TODO Evgeny: Тоже самое и тут. Удаление игроков возвращает количество удаленных записей, лучше пусть будет:
    /*
    suspend fun clearAllPlayers() : Int
     */

    @Query("DELETE FROM $PLAYERS")
//    suspend fun clearAllPlayers()
    suspend fun clearAllPlayers(): Int

    @Query("SELECT * FROM $PLAYERS WHERE $ID = :playerId")
    fun getSpecificPlayer(playerId: Int?): NbaPlayer
}