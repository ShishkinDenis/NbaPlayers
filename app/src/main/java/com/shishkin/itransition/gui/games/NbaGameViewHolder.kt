package com.shishkin.itransition.gui.games

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.shishkin.itransition.R
import com.shishkin.itransition.network.entities.NbaGame

class NbaGameViewHolder(itemView: View, private val listener: NbaGameItemListener) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {

    private lateinit var nbaGame: NbaGame

    // TODO   view/data binding
    val season: TextView = itemView.findViewById(R.id.tv_game_season)
    val gameStatus: TextView = itemView.findViewById(R.id.tv_game_status)
    val gameDate: TextView = itemView.findViewById(R.id.tv_game_date)
    val homeTeamScore: TextView = itemView.findViewById(R.id.tv_home_team_score)

    init {
        itemView.setOnClickListener(this)
    }
    // TODO delete
    fun setNbaItem(item: NbaGame) {
        this.nbaGame = item
    }

    override fun onClick(v: View?) {
        listener.onClickedNbaGame(nbaGame)
    }
}