package com.shishkin.itransition.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

const val ID = "id"
const val PLAYERS = "players"

@Entity(tableName = PLAYERS)
data class NbaPlayerLocal(
    @PrimaryKey val id: Int,
    @SerializedName("first_name") val firstName: String,
    @SerializedName("height_feet") val heightFeet: Int,
    @SerializedName("height_inches") val heightInches: Int,
    @SerializedName("last_name") val lastName: String,
    val position: String,
    val team: NbaTeamLocal,
    @SerializedName("weight_pounds") val weightPounds: Int
)