package com.shishkin.itransition.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.shishkin.itransition.network.entities.NbaTeam

class TeamConverter {
    @TypeConverter
    fun fromNbaTeamToString(nbaTeam: NbaTeam): String {
        return Gson().toJson(nbaTeam)
    }

    @TypeConverter
    fun toNbaTeamFromString(nbaTeam: String?): NbaTeam {
        return Gson().fromJson(nbaTeam, NbaTeam::class.java)
    }
}
