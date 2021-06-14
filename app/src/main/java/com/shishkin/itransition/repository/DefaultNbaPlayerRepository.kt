package com.shishkin.itransition.repository

import android.util.Log
import com.shishkin.itransition.db.NbaPlayerData
import com.shishkin.itransition.db.NbaPlayer
import com.shishkin.itransition.network.NbaPlayersApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject


class DefaultNbaPlayerRepository @Inject constructor() : NbaPlayerRepository {
    private val API_KEY = "6db3e9805dmsh48065f33193b2d0p1e1a19jsn8cc478ac8bdd"
    private val nbaPlayersApi: NbaPlayersApi? =
        com.shishkin.itransition.network.NbaPlayersApiClient.getClient()
            ?.create(NbaPlayersApi::class.java)


    override fun getNbaPlayersData(): Flow<NbaPlayerData?> {
        return flow {
            val flowData = nbaPlayersApi?.getAllNbaPlayers(API_KEY)
            emit(flowData)
        }.flowOn(Dispatchers.IO)

    }

    //     Coroutines + LiveData
//    override suspend fun getData(): Data? = nbaPlayersApi?.getAllNbaPlayers(API_KEY)


//    Call
//    override fun getNbaPlayersData() {
//    val call : Call<Data>? =  nbaPlayersApi?.getAllNbaPlayers(API_KEY)
//    call?.enqueue(object : Callback<Data> {
//        override fun onFailure(call: Call<Data>, t: Throwable) {
//            Log.d("Retrofit", t.toString() + "exception")
//        }
//
//        override fun onResponse(call: Call<Data>, response: Response<Data>) {
//            val playersList: List<NbaPlayer>? = response.body()?.getData()
//            Log.d("Retrofit", response.body().toString() + " success")
//            Log.d("Retrofit", playersList?.get(1)?.getName() + " success")
//        }
//
//    })
//}

//    TODO use Flow
    override fun getSpecificPlayer(playerId: Int) {
        val callSpecificPlayer: Call<NbaPlayer>? = nbaPlayersApi?.getSpecificPlayer(playerId, API_KEY)
        callSpecificPlayer?.enqueue(object : Callback<NbaPlayer> {
            override fun onFailure(call: Call<NbaPlayer>, t: Throwable) {
                Log.d("Retrofit", t.toString() + "exception")
            }

            override fun onResponse(call: Call<NbaPlayer>, response: Response<NbaPlayer>) {
                val playerName = response.body()?.getName()
                Log.d("Retrofit", "$playerName success")
            }

        })
    }


}