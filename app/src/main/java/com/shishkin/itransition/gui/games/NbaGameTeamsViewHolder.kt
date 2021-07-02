package com.shishkin.itransition.gui.games

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.shishkin.itransition.R

class NbaGameTeamsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    // TODO   view/data binding
    val homeTeamName: TextView = itemView.findViewById(R.id.tv_home_team_name)
    val homeTeamCity: TextView = itemView.findViewById(R.id.tv_home_team_city)
    val homeTeamAbbreviation: TextView = itemView.findViewById(R.id.tv_home_team_abbreviation)
    val homeTeamFullName: TextView = itemView.findViewById(R.id.tv_home_team_full_name)

    val visitorTeamName: TextView = itemView.findViewById(R.id.tv_visitor_team_name)
    val visitorTeamCity: TextView = itemView.findViewById(R.id.tv_visitor_team_city)
    val visitorTeamAbbreviation: TextView = itemView.findViewById(R.id.tv_visitor_team_abbreviation)
    val visitorTeamFullName: TextView = itemView.findViewById(R.id.tv_visitor_team_full_name)
}