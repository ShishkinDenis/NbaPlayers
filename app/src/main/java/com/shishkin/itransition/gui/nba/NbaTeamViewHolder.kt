package com.shishkin.itransition.gui.nba

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.shishkin.itransition.R
import com.shishkin.itransition.di.MyApplication.Companion.context
import com.shishkin.itransition.gui.utils.ListItem
import com.shishkin.itransition.network.entities.NbaTeam

class NbaTeamViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    // TODO   view/data binding
    val teamFullName: TextView = itemView.findViewById(R.id.tv_team_full_name)
    val nbaTeamCity: TextView = itemView.findViewById(R.id.tv_nba_team_city)

    fun bind(item: ListItem){
        val nbaTeam = item.item as? NbaTeam
            val teamFullName: String? = context?.getString(R.string.nba_team_full_name,nbaTeam?.fullName,nbaTeam?.abbreviation)
            val nbaTeamCity: String? = context?.getString(R.string.nba_team_city, nbaTeam?.city)
            this.teamFullName.text = teamFullName
            this.nbaTeamCity.text = nbaTeamCity
    }
}