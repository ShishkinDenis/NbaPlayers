package com.shishkin.itransition.gui.games

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.shishkin.itransition.R
import com.shishkin.itransition.gui.nba.lists.ListItem
import com.shishkin.itransition.gui.nba.lists.NbaPlayerItemDiffCallback
import com.shishkin.itransition.network.entities.NbaGame
import java.text.SimpleDateFormat
import java.util.*

//TODO implement header
class NbaGamesAdapter(
    private val gamesList: List<ListItem>?,
    private val listener: NbaGameItemListener
) :
    ListAdapter<ListItem, RecyclerView.ViewHolder>(NbaPlayerItemDiffCallback()) {

    companion object {
        const val VIEW_TYPE_NBA_GAME = 1
        const val VIEW_TYPE_NBA_TEAM = 2
    }

    override fun getItemViewType(position: Int): Int {
        return gamesList?.get(position)?.viewType!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.nba_game_status_adapter, parent, false)
        return NbaGameViewHolder(view, listener)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val nbaGame: NbaGame = gamesList?.get(position)?.item as NbaGame

        with(holder as NbaGameViewHolder) {
            season.text = "Season: " + nbaGame.season.toString()
            gameStatus.text = "Status: " + nbaGame.status
            gameDate.text = "Date: " + convertDate(nbaGame.date)
            homeTeamScore.text = "Home team score: " + nbaGame.homeTeamScore.toString()
            getNbaItem(nbaGame)
        }
    }

    private fun convertDate(date: Date): String? {
        val DATE_PATTERN = "yyyy-MM-dd"
        val sdf = SimpleDateFormat(DATE_PATTERN)
        return sdf.format(date)
    }

}

interface NbaGameItemListener {
    fun onClickedNbaGame(nbaGame: NbaGame)
}

//TODO move to separate file
class NbaGameViewHolder(itemView: View, private val listener: NbaGameItemListener) :
    RecyclerView.ViewHolder(itemView), View.OnClickListener {
    private lateinit var nbaGame: NbaGame

    // TODO   view/data binding
    val season: TextView = itemView.findViewById(R.id.tv_game_season)
    val gameStatus: TextView = itemView.findViewById(R.id.tv_game_status)
    val gameDate: TextView = itemView.findViewById(R.id.tv_game_date)
    val homeTeamScore: TextView = itemView.findViewById(R.id.tv_home_team_score)

    fun getNbaItem(item: NbaGame) {
        this.nbaGame = item
    }

    init {
        itemView.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        Log.d("Retrofit", "ID from NbaGameAdapter " + nbaGame.id.toString())
        listener.onClickedNbaGame(nbaGame)
    }

}

