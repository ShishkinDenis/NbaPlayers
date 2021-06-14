package com.shishkin.itransition.gui.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shishkin.itransition.R
import com.shishkin.itransition.db.NbaPlayerData
import com.shishkin.itransition.db.NbaPlayer
import com.shishkin.itransition.gui.nba.lists.adapters.NbaPlayersAdapter
import com.shishkin.itransition.network.NbaPlayersApi
import com.shishkin.itransition.network.NbaPlayersApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

//TODO delete this activity
class TestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

//        TODO move to NbaFragment, use flow
        val testRecycler = findViewById<RecyclerView>(R.id.activity_test_rv)
        val linearLayoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        testRecycler.layoutManager = linearLayoutManager

        val API_KEY = "6db3e9805dmsh48065f33193b2d0p1e1a19jsn8cc478ac8bdd"
        val nbaPlayersApi: NbaPlayersApi? = NbaPlayersApiClient.getClient()?.create(NbaPlayersApi::class.java)
        val call : Call<NbaPlayerData>? =  nbaPlayersApi?.getAllNbaPlayersUsingCall(API_KEY)

        call?.enqueue(object : Callback<NbaPlayerData> {
            override fun onFailure(call: Call<NbaPlayerData>, t: Throwable) {
                Log.d("Retrofit", t.toString() + "exception")
            }

            override fun onResponse(call: Call<NbaPlayerData>, response: Response<NbaPlayerData>) {
                val playersList: List<NbaPlayer>? = response.body()?.getNbaPlayersData()
                Log.d("Retrofit", response.body().toString() + " success")
                Log.d("Retrofit", playersList?.get(1)?.getName() + " success")
                val nbaPlayersAdapter : NbaPlayersAdapter = NbaPlayersAdapter(playersList);
                testRecycler.adapter = nbaPlayersAdapter
            }
        })
    }
}