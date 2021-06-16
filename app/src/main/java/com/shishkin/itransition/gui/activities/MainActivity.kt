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

//        TODO delete
        val team = NbaTeam(1, "a", "b", "c", "d", "e", "f")
        val nbaPlayer1 = NbaPlayer(1, "Andrew", 2, 3, "b", "c", team, 4)
        val nbaPlayer2 = NbaPlayer(2, "Jack", 2, 3, "b", "c", team, 4)
        val nbaPlayer3 = NbaPlayer(3, "John", 2, 3, "b", "c", team, 4)

//        val test = RestResponse<NbaPlayerData>(
//            data = NbaPlayerData(nbaPlayersList = listOf(nbaPlayer1, nbaPlayer2, nbaPlayer3)),
//            meta = Meta(0,0,0,0,0)
//        )
                val testPlayer = RestResponse<NbaPlayer>(data = nbaPlayer1,meta = Meta(0,0,0,0,0)
        )
        val test2 = RestResponse<List<NbaPlayer>>(
            data = listOf(nbaPlayer1, nbaPlayer2, nbaPlayer3),
            meta = Meta(0, 0, 0, 0, 0)
        )
        val gson = Gson()
//        val json = gson.toJson(test)
//        Log.d("GSON",json)

//        val json = gson.toJson(test2)
        val json = gson.toJson(testPlayer)
        Log.d("GSON", json)
    }
}

