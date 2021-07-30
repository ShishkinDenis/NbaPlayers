package com.shishkin.itransition.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [NbaPlayerLocal::class, NbaTeamLocal::class, UserLocal::class], version = 5)
abstract class NbaPlayerDataBase : RoomDatabase() {

    abstract fun nbaPlayerDao(): NbaPlayerDao
    abstract fun userDao(): UserDao

    companion object {

        private const val PLAYER_DB = "player.db"

        fun buildDatabase(context: Context) =
            Room.databaseBuilder(context, NbaPlayerDataBase::class.java, PLAYER_DB)
//                    TODO delete
                .fallbackToDestructiveMigration()
                .build()
    }
}

