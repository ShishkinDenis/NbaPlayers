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


class NbaPlayersAdapter(private val playersList: List<NbaPlayer>?,private val listener: NbaPlayerItemListener) :
    ListAdapter<NbaPlayer, NbaPlayerViewHolder>(NbaPlayerItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NbaPlayerViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.nba_players_adapter, parent, false)
        return NbaPlayerViewHolder(view,listener )
    }

    override fun onBindViewHolder(holder: NbaPlayerViewHolder, position: Int) {
//                TODO string resources and move to separate method
        val nbaPlayerName: String = playersList?.get(position)?.firstName + " " + playersList?.get(position)?.lastName
        val nbaTeamAbbreviation : String? = "Team: " + playersList?.get(position)?.team?.abbreviation
        val nbaTeamCity : String? = "City: " + playersList?.get(position)?.team?.city

        holder.nbaPlayerName.text = nbaPlayerName
        holder.nbaTeamAbbreviation.text = nbaTeamAbbreviation
        holder.nbaTeamCity.text = nbaTeamCity
        playersList?.get(position)?.let { holder.getItem(it) }

    }

    interface NbaPlayerItemListener {
        fun onClickedNbaPlayer(nbaPlayerId: Int)
    }

}
//TODO move to separate class
class NbaPlayerViewHolder(itemView: View, private val listener: NbaPlayersAdapter.NbaPlayerItemListener) : RecyclerView.ViewHolder(itemView), View.OnClickListener{
    private lateinit var nbaPlayer: NbaPlayer
    // TODO   view/data binding
    val nbaPlayerName: TextView = itemView.findViewById(R.id.tv_nba_player_first_and_last_name)
    val nbaTeamAbbreviation: TextView = itemView.findViewById(R.id.tv_nba_team_abbreviation)
    val nbaTeamCity: TextView = itemView.findViewById(R.id.tv_team_city)

    fun getItem(item : NbaPlayer){
        this.nbaPlayer = item
    }

    init {
        itemView.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
       Log.d("Retrofit", nbaPlayer.id.toString())
        listener.onClickedNbaPlayer(nbaPlayer.id)
    }
}
