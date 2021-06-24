package com.shishkin.itransition.network.entities

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

//TODO camelcase
@Parcelize
data class NbaTeam(
    val id: Int,
    val abbreviation: String,
    val city: String,
    val conference: String,
    val division: String,
    @SerializedName("full_name") val fullName: String,
    val name: String,
    val home_team_score: Int,
    val visitor_team_score: Int
) : Parcelable
