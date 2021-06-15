package com.shishkin.itransition.repository

import android.util.Log
import com.shishkin.itransition.network.NbaPlayersApi
import com.shishkin.itransition.network.entities.NbaPlayer
import com.shishkin.itransition.network.entities.NbaPlayerData
import com.shishkin.itransition.network.entities.RestResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject


class DefaultNbaPlayerRepository @Inject constructor() : NbaPlayerRepository {
    private val nbaPlayersApi: NbaPlayersApi? =
        com.shishkin.itransition.network.NbaPlayersApiClient.getClient()
            .create(NbaPlayersApi::class.java)





    override fun getNbaPlayersData(): Flow<RestResponse<NbaPlayerData>?> {
        return flow {
            val flowData = nbaPlayersApi?.getAllNbaPlayers()
            emit(flowData)
        }.flowOn(Dispatchers.IO)
    }

    //Flow
//    override fun getNbaPlayersData(): Flow<NbaPlayerData?> {
//        return flow {
//            val flowData = nbaPlayersApi?.getAllNbaPlayers()
//            emit(flowData)
//        }.flowOn(Dispatchers.IO)
//    }


    //     Coroutines + LiveData
//    override suspend fun getData(): Data? = nbaPlayersApi?.getAllNbaPlayers(API_KEY)


//    TODO use Flow
    override fun getSpecificPlayer(playerId: Int) {
        val callSpecificPlayer: Call<NbaPlayer>? = nbaPlayersApi?.getSpecificPlayer(playerId)
        callSpecificPlayer?.enqueue(object : Callback<NbaPlayer> {
            override fun onFailure(call: Call<NbaPlayer>, t: Throwable) {
                Log.d("Retrofit", t.toString() + "exception")
            }

            override fun onResponse(call: Call<NbaPlayer>, response: Response<NbaPlayer>) {
                val playerName = response.body()?.firstName
                Log.d("Retrofit", "$playerName success")
            }

        })
    }


}