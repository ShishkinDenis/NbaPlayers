package com.shishkin.itransition.gui.nba.lists

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.shishkin.itransition.R
import com.shishkin.itransition.network.entities.NbaPlayer
import com.shishkin.itransition.network.entities.NbaTeam

//TODO implement header
class NbaPlayersAdapter(
    private val playersList: List<ListItem>?,
    private val listener: NbaPlayerItemListener
) :
    ListAdapter<ListItem, RecyclerView.ViewHolder>(NbaPlayerItemDiffCallback()) {

    companion object {
        const val VIEW_TYPE_NBA_PLAYER = 1
        const val VIEW_TYPE_NBA_TEAM = 2
    }

    override fun getItemViewType(position: Int): Int {
        return playersList?.get(position)?.viewType!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_NBA_PLAYER) {
            val view: View =
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.nba_player_adapter, parent, false)
            NbaPlayerViewHolder(view, listener)
        } else {
            val view: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.nba_player_team_adapter, parent, false)
            TeamViewHolder(view, listener)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//                TODO string resources and move to separate method
        if (playersList?.get(position)?.viewType == VIEW_TYPE_NBA_PLAYER) {
            val nbaPlayer: NbaPlayer = (playersList[position].item as NbaPlayer)
            val nbaPlayerName: String =
                "NBA player: " + nbaPlayer.firstName + " " + nbaPlayer.lastName
            val nbaPlayerPosition: String = "Position: " + nbaPlayer.position
            (holder as NbaPlayerViewHolder).nbaPlayerName.text = nbaPlayerName
            holder.nbaPlayerPosition.text = nbaPlayerPosition
            holder.getNbaItem(nbaPlayer)
        }
//TODO implement separator between items (nba player + team)
        else {
            val nbaTeam: NbaTeam = (playersList?.get(position)?.item as NbaTeam)
            val teamFullName: String = "Team: " + nbaTeam.fullName + ", " + nbaTeam.abbreviation
            val nbaTeamCity: String? = "City: " + nbaTeam.city
            (holder as TeamViewHolder).teamFullName.text = teamFullName
            holder.nbaTeamCity.text = nbaTeamCity
        }
    }

    interface NbaPlayerItemListener {
        fun onClickedNbaPlayer(nbaPlayerId: Int)
    }
}

//TODO move to separate file
class NbaPlayerViewHolder(
    itemView: View,
    private val listener: NbaPlayersAdapter.NbaPlayerItemListener
) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
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
        listener.onClickedNbaPlayer(nbaPlayer.id)
    }

}

class TeamViewHolder(
    itemView: View,
    private val listener: NbaPlayersAdapter.NbaPlayerItemListener
) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
    // TODO   view/data binding
    val teamFullName: TextView = itemView.findViewById(R.id.tv_team_full_name)
    val nbaTeamCity: TextView = itemView.findViewById(R.id.tv_nba_team_city)
    override fun onClick(v: View?) {
        TODO("Not yet implemented")
    }

}


