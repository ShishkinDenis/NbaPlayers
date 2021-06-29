package com.shishkin.itransition.gui.games

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.shishkin.itransition.R
import com.shishkin.itransition.gui.utils.NbaListItemDiffCallback
import com.shishkin.itransition.network.entities.ListItem
import com.shishkin.itransition.network.entities.NbaGame
import java.text.SimpleDateFormat
import java.util.*

class NbaGamesAdapter(
        private val listener: NbaGameItemListener
) :
        PagingDataAdapter<ListItem, RecyclerView.ViewHolder>(NbaListItemDiffCallback()) {

    companion object {
        const val VIEW_TYPE_NBA_GAME = 1
        const val VIEW_TYPE_NBA_GAME_TEAM = 2
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position)!!.viewType
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_NBA_GAME) {
            val view: View =
                    LayoutInflater.from(parent.context)
                            .inflate(R.layout.nba_game_status_adapter, parent, false)
            NbaGameViewHolder(view, listener)
        } else {
            val view: View =
                    LayoutInflater.from(parent.context)
                            .inflate(R.layout.nba_game_teams_adapter, parent, false)
            NbaGameTeamsViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItem(position)?.viewType == VIEW_TYPE_NBA_GAME) {
            val nbaGame: NbaGame = (getItem(position)?.item as NbaGame)
            with(holder as NbaGameViewHolder) {
                season.text = "Season: " + nbaGame.season.toString()
                gameStatus.text = "Status: " + nbaGame.status
                gameDate.text = "Date: " + convertDate(nbaGame.date)
                homeTeamScore.text = "Home team score: " + nbaGame.homeTeamScore.toString()
                getNbaItem(nbaGame)
            }
        } else {
            val nbaGame: NbaGame = (getItem(position)?.item as NbaGame)
            with(holder as NbaGameTeamsViewHolder) {

                homeTeamName.text = "Name: " + nbaGame.homeTeam.name
                homeTeamCity.text = "City: " + nbaGame.homeTeam.city
                homeTeamAbbreviation.text = "Abbreviation: " + nbaGame.homeTeam.abbreviation
                homeTeamFullName.text = nbaGame.homeTeam.fullName

                visitorTeamName.text = "Name: " + nbaGame.visitorTeam.name
                visitorTeamCity.text = "City: " + nbaGame.visitorTeam.city
                visitorTeamAbbreviation.text = "Abbreviation: " + nbaGame.visitorTeam.abbreviation
                visitorTeamFullName.text = nbaGame.visitorTeam.fullName
            }
        }
    }

    private fun convertDate(date: Date): String? {
        val DATE_PATTERN = "yyyy-MM-dd"
        val sdf = SimpleDateFormat(DATE_PATTERN)
        return sdf.format(date)
    }

}



