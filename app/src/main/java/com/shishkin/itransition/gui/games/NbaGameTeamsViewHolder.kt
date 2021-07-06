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
            tvItemNbaGameTeamsHomeTeamName.text = context.getString(
                R.string.nba_game_home_team_name, nbaGame.homeTeam.name
            )

            tvItemNbaGameTeamsHomeTeamCity.text = context.getString(
                R.string.nba_game_home_team_city, nbaGame.homeTeam.city
            )

            tvItemNbaGameTeamsHomeTeamAbbreviation.text = context.getString(
                R.string.nba_game_home_team_abbreviation, nbaGame.homeTeam.abbreviation
            )

            tvItemNbaGameTeamsHomeTeamFullName.text = nbaGame.homeTeam.fullName

            tvItemNbaGameTeamsVisitorTeamName.text = context.getString(
                R.string.nba_game_visitor_team_name, nbaGame.visitorTeam.name
            )

            tvItemNbaGamesTeamsVisitorTeamCity.text = context.getString(
                R.string.nba_game_visitor_team_city, nbaGame.visitorTeam.city
            )

            tvItemNbaGameTeamsVisitorTeamAbbreviation.text = context.getString(
                R.string.nba_game_visitor_team_abbreviation, nbaGame.visitorTeam.abbreviation
            )

            tvItemNbaGameTeamsVisitorTeamFullName.text = nbaGame.visitorTeam.fullName
        }

    }
}