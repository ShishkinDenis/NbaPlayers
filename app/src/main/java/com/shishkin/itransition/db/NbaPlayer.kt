package com.shishkin.itransition.db

import com.google.gson.annotations.SerializedName

//@Entity
//TODO make data class
class NbaPlayer {
    @SerializedName("id")
    private val id: String? = null

    @SerializedName("first_name")
    private val firstName: String? = null

//    TODO complete entity
    //    height_feet
//    height_inches
//    last_name
//    position
//    weight_pounds

    fun getName(): String? {
        return firstName
    }
}