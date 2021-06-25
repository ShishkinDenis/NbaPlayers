package com.shishkin.itransition.gui.nba

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.shishkin.itransition.R
import com.shishkin.itransition.gui.utils.NbaListItemDiffCallback
import com.shishkin.itransition.network.entities.ListItem
import com.shishkin.itransition.network.entities.NbaPlayer
import com.shishkin.itransition.network.entities.NbaTeam


class NbaPlayersPaginationAdapter(private val listenerPagination: NbaPlayerPaginationItemListener) :
    PagingDataAdapter<ListItem, RecyclerView.ViewHolder>(NbaListItemDiffCallback()) {

    companion object {
        const val VIEW_TYPE_NBA_PLAYER = 1
        const val VIEW_TYPE_NBA_TEAM = 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_NBA_PLAYER) {
            val view: View =
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.nba_player_adapter, parent, false)
            NbaPlayerPaginationViewHolder(
                view,listenerPagination
            )
        } else {
            val view: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.nba_player_team_adapter, parent, false)
            TeamPaginationViewHolder(
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
                "NBA player: " + nbaPlayer?.firstName + " " + nbaPlayer?.lastName
            val nbaPlayerPosition: String = "Position: " + nbaPlayer?.position
            (holder as NbaPlayerPaginationViewHolder).nbaPlayerName.text = nbaPlayerName
            holder.nbaPlayerPosition.text = nbaPlayerPosition
            nbaPlayer?.let { holder.getNbaItem(it) }
        } else {
            val nbaTeam: NbaTeam = (getItem(position)?.item as NbaTeam)
            val teamFullName: String = "Team: " + nbaTeam.fullName + ", " + nbaTeam.abbreviation
            val nbaTeamCity: String? = "City: " + nbaTeam.city
            (holder as TeamPaginationViewHolder).teamFullName.text = teamFullName
            holder.nbaTeamCity.text = nbaTeamCity
        }
    }
}

interface NbaPlayerPaginationItemListener {
    fun onClickedNbaPlayer(nbaPlayerId: Int)
}


class NbaPlayerPaginationViewHolder(itemView: View, private val listenerPagination: NbaPlayerPaginationItemListener) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
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

class TeamPaginationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    // TODO   view/data binding
    val teamFullName: TextView = itemView.findViewById(R.id.tv_team_full_name)
    val nbaTeamCity: TextView = itemView.findViewById(R.id.tv_nba_team_city)

}



