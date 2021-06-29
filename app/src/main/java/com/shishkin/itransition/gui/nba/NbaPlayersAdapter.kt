package com.shishkin.itransition.gui.nba

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.shishkin.itransition.R
import com.shishkin.itransition.gui.utils.NbaPlayerDiffCallback
import com.shishkin.itransition.network.entities.NbaPlayer

class NbaPlayersAdapter(private val listener: NbaPlayerItemListener) :
    ListAdapter<NbaPlayer, RecyclerView.ViewHolder>(NbaPlayerDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.nba_player_adapter, parent, false)
        return NbaPlayerViewHolder(view,listener)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val nbaPlayer: NbaPlayer = getItem(position)!!
        val nbaPlayerName: String =
            "NBA player: " + nbaPlayer?.firstName + " " + nbaPlayer?.lastName
        val nbaPlayerPosition: String = "Position: " + nbaPlayer?.position
        (holder as NbaPlayerViewHolder).nbaPlayerName.text = nbaPlayerName
        holder.nbaPlayerPosition.text = nbaPlayerPosition
        nbaPlayer?.let { holder.getNbaItem(it) }

    }
}

interface NbaPlayerItemListener {
    fun onClickedNbaPlayer(nbaPlayerId: Int)
}


class NbaPlayerViewHolder(itemView: View, private val listenerPagination: NbaPlayerItemListener) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
    private lateinit var nbaPlayer: NbaPlayer

    // TODO   view/data binding
    val nbaPlayerName: TextView = itemView.findViewById(R.id.tv_game_season)
    val nbaPlayerPosition: TextView = itemView.findViewById(R.id.tv_game_status)

    fun getNbaItem(item: NbaPlayer) {
        this.nbaPlayer = item
    }

    init {
        itemView.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        Log.d("Retrofit", "ID from adapter " + nbaPlayer.id.toString())
        listenerPagination.onClickedNbaPlayer(nbaPlayer.id)
    }

}
