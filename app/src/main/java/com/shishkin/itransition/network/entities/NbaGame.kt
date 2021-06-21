package com.shishkin.itransition.network.entities

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class NbaGame(
    val id: Int,
    val date: Date,
    @SerializedName("home_team") val homeTeam: NbaTeam,
    @SerializedName("home_team_score") val homeTeamScore: Int,
    val period: Int,
    val postseason: Boolean,
    val season: Int,
    val status: String,
    val time: String,
    @SerializedName("visitor_team") val visitorTeam: NbaTeam,
    @SerializedName("visitor_team_score") val visitorTeamScore: Int
) : Parcelable



