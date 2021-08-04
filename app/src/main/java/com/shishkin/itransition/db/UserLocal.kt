package com.shishkin.itransition.db

import androidx.room.Entity
import androidx.room.PrimaryKey

const val USER_TABLE = "user"

@Entity(tableName = USER_TABLE)
data class UserLocal(
    @PrimaryKey val id: Int,
    val name: String,
    val birthDate: String,
    val profileImageUri: String
)
