package com.shishkin.itransition.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remotekeys")
//data class RemoteKeys(@PrimaryKey val repoId: String, val prevKey: Int?, val nextKey: Int?)
data class RemoteKeys(
    @PrimaryKey val id: Int,
    val prevKey: Int?,
    val nextKey: Int?
    )