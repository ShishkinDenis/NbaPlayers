package com.shishkin.itransition.network

import android.content.Context
import com.shishkin.itransition.di.Application
import javax.inject.Inject

class RawFileReader @Inject constructor(@Application val context: Context) {

    fun loadJSONFromRaw(raw: Int) : String{
        return context.resources.openRawResource(raw)
            .bufferedReader().use { it.readText() }
    }
}