package com.shishkin.itransition.gui.nba.lists.pagination

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.shishkin.itransition.R
import com.shishkin.itransition.network.entities.NbaPlayer


//TODO ListItem
class NbaPlayersPaginationAdapter() :
    PagingDataAdapter<NbaPlayer, RecyclerView.ViewHolder>(NbaPlayerItemPaginationDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.nba_player_adapter, parent, false)
        return NbaPlayerPaginationViewHolder(
            view
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val nbaPlayer: NbaPlayer? = getItem(position)
        val nbaPlayerName: String = "NBA player: " + nbaPlayer?.firstName + " " + nbaPlayer?.lastName
        val nbaPlayerPosition: String = "Position: " + nbaPlayer?.position

            (holder as NbaPlayerPaginationViewHolder).nbaPlayerName.text = nbaPlayerName
            holder.nbaPlayerPosition.text = nbaPlayerPosition
    }
}

class NbaPlayerPaginationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private lateinit var nbaPlayer: NbaPlayer

    // TODO   view/data binding
    val nbaPlayerName: TextView = itemView.findViewById(R.id.tv_game_season)
    val nbaPlayerPosition: TextView = itemView.findViewById(R.id.tv_game_status)

    fun getNbaItem(item: NbaPlayer) {
        this.nbaPlayer = item
    }

}



