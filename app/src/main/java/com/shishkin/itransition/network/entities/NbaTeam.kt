package com.shishkin.itransition.network.entities

import com.google.gson.annotations.SerializedName

data class NbaTeam(
    val id: Int,
    val abbreviation: String,
    val city: String,
    val conference: String,
    val division: String,
    @SerializedName("full_name") val fullName: String,
    val name: String
)
