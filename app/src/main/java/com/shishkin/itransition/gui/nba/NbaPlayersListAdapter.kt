package com.shishkin.itransition.gui.nba

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.shishkin.itransition.R
import com.shishkin.itransition.gui.utils.NbaListItemDiffCallback
import com.shishkin.itransition.network.entities.ListItem
import com.shishkin.itransition.network.entities.NbaPlayer
import com.shishkin.itransition.network.entities.NbaTeam

class NbaPlayersListAdapter(private val listener: NbaPlayerItemListener) :
        ListAdapter<ListItem, RecyclerView.ViewHolder>(NbaListItemDiffCallback()) {

    companion object {
        const val VIEW_TYPE_NBA_PLAYER = 1
        const val VIEW_TYPE_NBA_TEAM = 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_NBA_PLAYER) {
            val view: View =
                    LayoutInflater.from(parent.context)
                            .inflate(R.layout.nba_player_adapter, parent, false)
            NbaPlayerViewHolder(
                    view, listener
            )
        } else {
            val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.nba_player_team_adapter, parent, false)
            TeamViewHolder(
                    view
            )
        }
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position)!!.viewType
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItem(position)?.viewType == VIEW_TYPE_NBA_PLAYER) {
            val nbaPlayer: NbaPlayer = getItem(position)?.item as NbaPlayer
            val nbaPlayerName: String =
                    "NBA player: " + nbaPlayer.firstName + " " + nbaPlayer.lastName
            val nbaPlayerPosition: String = "Position: " + nbaPlayer.position
            (holder as NbaPlayerViewHolder).nbaPlayerName.text = nbaPlayerName
            holder.nbaPlayerPosition.text = nbaPlayerPosition
            nbaPlayer.let { holder.getNbaItem(it) }
        } else {
            val nbaTeam: NbaTeam = (getItem(position)?.item as NbaTeam)
            val teamFullName: String = "Team: " + nbaTeam.fullName + ", " + nbaTeam.abbreviation
            val nbaTeamCity: String = "City: " + nbaTeam.city
            (holder as TeamViewHolder).teamFullName.text = teamFullName
            holder.nbaTeamCity.text = nbaTeamCity
        }
    }
}



