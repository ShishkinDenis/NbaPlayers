package com.shishkin.itransition.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.shishkin.itransition.network.entities.NbaPlayer

// TODO Evgeny: Я бы рекомендовал не использовать хардкод значения для данных из БД:
// players, id и прочие. Лучше их делать константами и расположить в классе NbaPlayer.
// Тогда если поменять придется название колонки (например поменять height_inches назвнание везде,
// тогда это будет сделать проще простого).

@Dao
interface NbaPlayerDao {

    @Query("SELECT * FROM players")
    fun getAllPlayers(): List<NbaPlayer>

    // TODO Evgeny: Когда ты вставляешь данные из БД, оно тебе возвращает список ID. Как правильно,
    // лучше его добавлять и тут, т.е:
    /*
    fun insertAllPlayers(nbaPlayers: List<NbaPlayer>) : List<Long>
     */

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllPlayers(nbaPlayers: List<NbaPlayer>)

    // TODO Evgeny: Тоже самое и тут. Удаление игроков возвращает количество удаленных записей, лучше пусть будет:
    /*
    suspend fun clearAllPlayers() : Int
     */

    @Query("DELETE FROM players")
    suspend fun clearAllPlayers()

    @Query("SELECT * FROM players WHERE id = :playerId")
    fun getSpecificPlayer(playerId: Int?): NbaPlayer

}