package com.shishkin.itransition.gui.games

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.shishkin.itransition.R
import com.shishkin.itransition.gui.utils.ListItem
import com.shishkin.itransition.gui.utils.NbaListItemDiffCallback
import com.shishkin.itransition.network.entities.NbaGame
import java.text.SimpleDateFormat
import java.util.*

const val VIEW_TYPE_NBA_GAME = 1
const val VIEW_TYPE_NBA_GAME_TEAM = 2
private const val NBA_DATE_FORMAT  =  "yyyy-MM-dd"

class NbaGamesAdapter(private val listener: NbaGameItemListener)
    : PagingDataAdapter<ListItem, RecyclerView.ViewHolder>(NbaListItemDiffCallback()) {

    override fun getItemViewType(position: Int): Int {
        return getItem(position)?.viewType ?: VIEW_TYPE_NBA_GAME
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_NBA_GAME) {
            val view: View = LayoutInflater.from(parent.context)
                            .inflate(R.layout.nba_game_status_adapter, parent, false)
            NbaGameViewHolder(view, listener)
        } else {
            val view: View = LayoutInflater.from(parent.context)
                            .inflate(R.layout.nba_game_teams_adapter, parent, false)
            NbaGameTeamsViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItem(position)?.viewType == VIEW_TYPE_NBA_GAME) {
            val nbaGame: NbaGame? = (getItem(position)?.item as? NbaGame)
            with(holder as NbaGameViewHolder) {
                season.text = itemView.context.getString(R.string.nba_game_season, nbaGame?.season)
                gameStatus.text = itemView.context.getString(R.string.nba_game_status, nbaGame?.status)
                gameDate.text = itemView.context.getString(R.string.nba_game_date,convertDate(nbaGame?.date))
                homeTeamScore.text = itemView.context.getString(R.string.nba_game_home_team_score,
                    nbaGame?.homeTeamScore
                )
                nbaGame?.let { setNbaItem(it) }
            }
        } else {
            val nbaGame: NbaGame? = (getItem(position)?.item as? NbaGame)
            with(holder as NbaGameTeamsViewHolder) {

                homeTeamName.text = itemView.context.getString(R.string.nba_game_home_team_name,
                    nbaGame?.homeTeam?.name)
                homeTeamCity.text = itemView.context.getString(R.string.nba_game_home_team_city,
                    nbaGame?.homeTeam?.city)
                homeTeamAbbreviation.text = itemView.context.getString(R.string.nba_game_home_team_abbreviation,
                    nbaGame?.homeTeam?.abbreviation)
                homeTeamFullName.text = nbaGame?.homeTeam?.fullName

                visitorTeamName.text = itemView.context.getString(R.string.nba_game_visitor_team_name,
                    nbaGame?.visitorTeam?.name)
                visitorTeamCity.text = itemView.context.getString(R.string.nba_game_visitor_team_city,
                    nbaGame?.visitorTeam?.city)
                visitorTeamAbbreviation.text = itemView.context.getString(R.string.nba_game_visitor_team_abbreviation,
                    nbaGame?.visitorTeam?.abbreviation)
                visitorTeamFullName.text = nbaGame?.visitorTeam?.fullName
            }
        }
    }

    private fun convertDate(date: Date?): String? {
        val sdf = SimpleDateFormat(NBA_DATE_FORMAT, Locale.US)
        return sdf.format(date)
    }
}



