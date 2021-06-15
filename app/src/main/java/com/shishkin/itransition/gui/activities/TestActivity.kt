package com.shishkin.itransition.gui.activities

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shishkin.itransition.R

import dagger.android.support.DaggerAppCompatActivity

//TODO delete this activity
class TestActivity : DaggerAppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

//        TODO move to NbaFragment, use flow
        val testRecycler = findViewById<RecyclerView>(R.id.activity_test_rv)
        val linearLayoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        testRecycler.layoutManager = linearLayoutManager

//        val nbaPlayersApi: NbaPlayersApi? = NbaPlayersApiClient.getClient()?.create(NbaPlayersApi::class.java)
//        val call : Call<NbaPlayerData>? =  nbaPlayersApi?.getAllNbaPlayersUsingCall()
//
//        call?.enqueue(object : Callback<NbaPlayerData> {
//            override fun onFailure(call: Call<NbaPlayerData>, t: Throwable) {
//                Log.d("Retrofit", t.toString() + "exception")
//            }
//
//            override fun onResponse(call: Call<NbaPlayerData>, response: Response<NbaPlayerData>) {
//                val playersList: List<NbaPlayer>? = response.body()?.getNbaPlayersData()
//                Log.d("Retrofit", response.body().toString() + " success")
//                Log.d("Retrofit", playersList?.get(1)?.getName() + " success")
//                val nbaPlayersAdapter : NbaPlayersAdapter = NbaPlayersAdapter(playersList);
//                testRecycler.adapter = nbaPlayersAdapter
//            }
//        })
    }
}