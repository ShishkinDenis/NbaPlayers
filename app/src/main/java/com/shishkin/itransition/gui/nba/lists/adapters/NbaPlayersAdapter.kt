package com.shishkin.itransition.gui.nba.lists.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.shishkin.itransition.R
import com.shishkin.itransition.network.entities.NbaPlayer


class NbaPlayersAdapter(private val playersList: List<NbaPlayer>?) :
    RecyclerView.Adapter<NbaPlayersAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.nba_players_adapter, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
//  TODO    !! remove
        return playersList?.size!!
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val nbaPlayerName: String =
            playersList?.get(position)?.first_name + " " + playersList?.get(position)?.last_name
        holder.nbaPlayerFirstName.text = nbaPlayerName
        holder.nbaTeamAbbreviation.text = playersList?.get(position)?.team?.abbreviation
    }

    //    TODO move to another file
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nbaPlayerFirstName: TextView =
            itemView.findViewById(R.id.tv_nba_player_first_and_last_name)
        val nbaTeamAbbreviation: TextView = itemView.findViewById(R.id.tv_nba_team_abbreviation)

    }
}