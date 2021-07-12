package com.shishkin.itransition

import android.content.Context
import com.google.gson.Gson
import com.shishkin.itransition.di.Application
import com.shishkin.itransition.network.entities.NbaConfiguration
import javax.inject.Inject

class JsonToNbaConfigurationConverter @Inject constructor(@Application val context: Context) {

    fun convertJsonToNbaConfiguration(): NbaConfiguration {
        val text = context.resources.openRawResource(R.raw.nba_configuration)
            .bufferedReader().use { it.readText() }
        return Gson().fromJson(text, NbaConfiguration::class.java)
    }
}