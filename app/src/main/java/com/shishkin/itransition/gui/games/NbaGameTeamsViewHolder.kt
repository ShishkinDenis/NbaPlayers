package com.shishkin.itransition.gui.games

import androidx.recyclerview.widget.RecyclerView
import com.shishkin.itransition.R
import com.shishkin.itransition.databinding.ItemNbaGameTeamsBinding
import com.shishkin.itransition.network.entities.NbaGame

class NbaGameTeamsViewHolder(private val binding: ItemNbaGameTeamsBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(nbaGame: NbaGame) {
        with(binding) {
            val context = root.context
            val homeTeamRemote = nbaGame.homeTeamRemote
            val visitorTeamRemote = nbaGame.visitorTeamRemote

            tvItemNbaGameTeamsHomeTeamName.text = context.getString(
                R.string.nba_game_home_team_name, homeTeamRemote.name
            )

            tvItemNbaGameTeamsHomeTeamCity.text = context.getString(
                R.string.nba_game_home_team_city, homeTeamRemote.city
            )

            tvItemNbaGameTeamsHomeTeamAbbreviation.text = context.getString(
                R.string.nba_game_home_team_abbreviation, homeTeamRemote.abbreviation
            )

            tvItemNbaGameTeamsHomeTeamFullName.text = homeTeamRemote.fullName

            tvItemNbaGameTeamsVisitorTeamName.text = context.getString(
                R.string.nba_game_visitor_team_name, visitorTeamRemote.name
            )

            tvItemNbaGamesTeamsVisitorTeamCity.text = context.getString(
                R.string.nba_game_visitor_team_city, visitorTeamRemote.city
            )

            tvItemNbaGameTeamsVisitorTeamAbbreviation.text = context.getString(
                R.string.nba_game_visitor_team_abbreviation, visitorTeamRemote.abbreviation
            )

            tvItemNbaGameTeamsVisitorTeamFullName.text = visitorTeamRemote.fullName
        }
    }
}