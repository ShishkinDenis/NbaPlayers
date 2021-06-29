package com.shishkin.itransition.gui.nba

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.shishkin.itransition.R

class TeamViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    // TODO   view/data binding
    val teamFullName: TextView = itemView.findViewById(R.id.tv_team_full_name)
    val nbaTeamCity: TextView = itemView.findViewById(R.id.tv_nba_team_city)

}