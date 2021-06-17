package com.shishkin.itransition.gui.activities

import android.os.Bundle
import android.util.Log
import com.google.gson.Gson
import com.shishkin.itransition.R
import com.shishkin.itransition.network.entities.Meta
import com.shishkin.itransition.network.entities.NbaPlayer
import com.shishkin.itransition.network.entities.NbaTeam
import com.shishkin.itransition.network.entities.RestResponse
import dagger.android.support.DaggerAppCompatActivity


class MainActivity : DaggerAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}

