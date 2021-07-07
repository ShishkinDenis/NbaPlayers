package com.shishkin.itransition.network.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

// TODO Evgeny: Почему в  пакете network entities сущность для базы данных?
// Сущности для БД и для Network, для UI - всегда разные

const val ID = "id"
const val PLAYERS = "players"

@Entity(tableName = PLAYERS)
data class NbaPlayer(
//    @PrimaryKey  val id: Int,
    @PrimaryKey @SerializedName("id") val nbaPlayerId: Int,
    @SerializedName("first_name") val firstName: String,
    @SerializedName("height_feet") val heightFeet: Int,
    @SerializedName("height_inches") val heightInches: Int,
    @SerializedName("last_name") val lastName: String,
    val position: String,
    @Embedded val team: NbaTeam,
//       val team: NbaTeam,
    @SerializedName("weight_pounds") val weightPounds: Int
)


