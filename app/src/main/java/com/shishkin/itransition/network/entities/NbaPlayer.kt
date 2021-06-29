package com.shishkin.itransition.network.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "players")
data class NbaPlayer(
        @PrimaryKey val id: Int,
        @SerializedName("first_name") val firstName: String,
        @SerializedName("height_feet") val heightFeet: Int,
        @SerializedName("height_inches") val heightInches: Int,
        @SerializedName("last_name") val lastName: String, val position: String, val team: NbaTeam,
        @SerializedName("weight_pounds") val weightPounds: Int
)


