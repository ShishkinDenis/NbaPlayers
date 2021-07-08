package com.shishkin.itransition.network.entities

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/*
TODO Evgeny
Не совсем правильно составлена архитектура БД.
У нас NbaTeam должна быть отдельная таблица в БД, а не обьект Parcelize который мы
используем через TeamConverter.

Достали игроков, всунули команды в NbaTeam таблицу.
Очевидно, что в одной команде может быть несколько игроков.
Поэтому тут у нас связь будет 1 ко многим: 1 команда и много игроков. Смотри:
Define one-to-many relationships
https://developer.android.com/training/data-storage/room/relationships
 */
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

