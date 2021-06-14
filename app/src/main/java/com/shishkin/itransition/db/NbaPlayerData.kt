package com.shishkin.itransition.db

import com.google.gson.annotations.SerializedName

//TODO make data class
class NbaPlayerData {

    @SerializedName("data")
    private val data: List<NbaPlayer>? = null

    fun getNbaPlayersData(): List<NbaPlayer>? {
        return data
    }


}