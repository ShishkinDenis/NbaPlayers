package com.shishkin.itransition.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.shishkin.itransition.network.entities.NbaPlayer

@Dao
interface NbaPlayerDao{

    @Query("SELECT * FROM players")
    fun getAllPlayers(): PagingSource<Int, NbaPlayer>
//    fun getAllPlayers(): PagingSource<Int, ListItem>

//    TODO listItem?
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllPlayers(nbaPlayers: List<NbaPlayer>)

    @Query("DELETE FROM players")
    suspend fun clearAllPlayers()

}