package com.shishkin.itransition.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Query("SELECT * FROM $USER_TABLE")
    fun getUser(): Flow<UserLocal?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(userLocal: UserLocal): Long
}