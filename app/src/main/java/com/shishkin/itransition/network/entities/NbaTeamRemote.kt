package com.shishkin.itransition.network.entities

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NbaTeamRemote(
        val id: Int,
        val abbreviation: String,
        val city: String,
        val conference: String,
        val division: String,
        @SerializedName("full_name") val fullName: String,
        val name: String,
        @SerializedName("home_team_score") val homeTeamScore: Int,
        @SerializedName("visitor_team_score") val visitorTeamScore: Int
) : Parcelable

