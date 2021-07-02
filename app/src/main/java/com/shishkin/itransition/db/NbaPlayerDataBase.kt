package com.shishkin.itransition.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.shishkin.itransition.network.entities.NbaPlayer

@TypeConverters(TeamConverter::class)
@Database(entities = [NbaPlayer::class], version = 1)
abstract class NbaPlayerDataBase : RoomDatabase() {

    abstract fun nbaPlayerDao(): NbaPlayerDao

    companion object {

        private const val PLAYER_DB = "player.db"

        fun buildDatabase(context: Context) =
                Room.databaseBuilder(
                        context,
                        NbaPlayerDataBase::class.java, PLAYER_DB
                ).build()
    }
}
