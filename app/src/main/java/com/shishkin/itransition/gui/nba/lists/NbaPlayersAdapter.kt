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


class NbaPlayersAdapter(
    private val playersList: List<NbaPlayer>?,
    private val listener: NbaPlayerItemListener
) :
    ListAdapter<NbaPlayer, RecyclerView.ViewHolder>(NbaPlayerItemDiffCallback()) {

    override fun getItemViewType(position: Int): Int {
        return position % 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == 0) {
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
        when (holder) {
            is NbaPlayerViewHolder -> {
                val nbaPlayerName: String =
                    playersList?.get(position)?.firstName + " " + playersList?.get(position)?.lastName
                val nbaTeamAbbreviation: String? =
                    "Team: " + playersList?.get(position)?.team?.abbreviation
                val nbaTeamCity: String? = "City: " + playersList?.get(position)?.team?.city

                holder.nbaPlayerName.text = nbaPlayerName
                holder.nbaTeamAbbreviation.text = nbaTeamAbbreviation
                holder.nbaTeamCity.text = nbaTeamCity
                playersList?.get(position)?.let { holder.getItem(it) }
            }
            is TeamViewHolder -> {
                val teamFullName: String = playersList?.get(position)?.team?.fullName.toString()
                val nbaTeamAbbreviation: String? =
                    "Team: " + playersList?.get(position)?.team?.abbreviation
                val nbaTeamCity: String? = "City: " + playersList?.get(position)?.team?.city

                holder.teamFullName.text = teamFullName
                holder.teamAbbreviation.text = nbaTeamAbbreviation
                holder.nbaTeamCity.text = nbaTeamCity
            }
        }
    }

    interface NbaPlayerItemListener {
        fun onClickedNbaPlayer(nbaPlayerId: Int)
    }
}

//TODO move to separate class
class NbaPlayerViewHolder(
    itemView: View,
    private val listener: NbaPlayersAdapter.NbaPlayerItemListener
) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
    private lateinit var nbaPlayer: NbaPlayer

    // TODO   view/data binding
    val nbaPlayerName: TextView = itemView.findViewById(R.id.tv_nba_player_first_and_last_name)
    val nbaTeamAbbreviation: TextView = itemView.findViewById(R.id.tv_nba_team_abbreviation)
    val nbaTeamCity: TextView = itemView.findViewById(R.id.tv_position)

    fun getItem(item: NbaPlayer) {
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
    val teamAbbreviation: TextView = itemView.findViewById(R.id.tv_nba_team_abb)
    val nbaTeamCity: TextView = itemView.findViewById(R.id.tv_team_city)
    override fun onClick(v: View?) {
        TODO("Not yet implemented")
    }

}


