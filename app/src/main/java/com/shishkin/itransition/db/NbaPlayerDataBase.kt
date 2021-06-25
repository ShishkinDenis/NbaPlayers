package com.shishkin.itransition.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.shishkin.itransition.network.entities.NbaPlayer
@TypeConverters(TeamConverter::class)
@Database(entities = [NbaPlayer::class, RemoteKeys::class], version = 1)
abstract class NbaPlayerDataBase : RoomDatabase(){
    abstract fun nbaPlayerDao(): NbaPlayerDao
    abstract fun remoteKeysDao(): RemoteKeysDao

    companion object {

        val PLAYER_DB = "player.db"

        @Volatile
        private var INSTANCE: NbaPlayerDataBase? = null

        fun getInstance(context: Context): NbaPlayerDataBase =
            INSTANCE ?: synchronized(this) {
                INSTANCE
                    ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext, NbaPlayerDataBase::class.java, PLAYER_DB)
                .build()
    }

}


//@TypeConverters(TeamConverter::class)
//@Database(entities = [NbaPlayer::class, RemoteKeys::class], version = 1)
//abstract class NbaPlayerDataBase : RoomDatabase(){
//    abstract fun nbaPlayerDao(): NbaPlayerDao
//    abstract fun remoteKeysDao(): RemoteKeysDao
//
//    companion object {
//
//        val PLAYER_DB = "player.db"
//
//        @Volatile
//        private var INSTANCE: NbaPlayerDataBase? = null
//
//        fun getInstance(context: Context): NbaPlayerDataBase =
//            INSTANCE ?: synchronized(this) {
//                INSTANCE
//                    ?: buildDatabase(context).also { INSTANCE = it }
//            }
//
//        private fun buildDatabase(context: Context) =
//            Room.databaseBuilder(context.applicationContext, NbaPlayerDataBase::class.java, PLAYER_DB)
//                .build()
//    }
//
//}