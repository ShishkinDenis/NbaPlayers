package com.shishkin.itransition.db

import androidx.room.TypeConverter
import com.google.gson.Gson

// TODO Evgeny: смотри NbaTeam
class TeamConverter {

    @TypeConverter
    fun fromNbaTeamLocalToString(nbaTeamLocal: NbaTeamLocal): String {
        return Gson().toJson(nbaTeamLocal)
    }

    @TypeConverter
    fun toNbaTeamLocalFromString(nbaTeamLocal: String?): NbaTeamLocal {
        return Gson().fromJson(nbaTeamLocal, NbaTeamLocal::class.java)
    }
}

