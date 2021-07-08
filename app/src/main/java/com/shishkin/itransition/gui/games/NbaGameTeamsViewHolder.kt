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
                R.string.nba_game_home_team_name, nbaGame.homeTeamRemote.name
            )

            tvItemNbaGameTeamsHomeTeamCity.text = context.getString(
                R.string.nba_game_home_team_city, nbaGame.homeTeamRemote.city
            )

            tvItemNbaGameTeamsHomeTeamAbbreviation.text = context.getString(
                R.string.nba_game_home_team_abbreviation, nbaGame.homeTeamRemote.abbreviation
            )

            tvItemNbaGameTeamsHomeTeamFullName.text = nbaGame.homeTeamRemote.fullName

            tvItemNbaGameTeamsVisitorTeamName.text = context.getString(
                R.string.nba_game_visitor_team_name, nbaGame.visitorTeamRemote.name
            )

            tvItemNbaGamesTeamsVisitorTeamCity.text = context.getString(
                R.string.nba_game_visitor_team_city, nbaGame.visitorTeamRemote.city
            )

            tvItemNbaGameTeamsVisitorTeamAbbreviation.text = context.getString(
                R.string.nba_game_visitor_team_abbreviation, nbaGame.visitorTeamRemote.abbreviation
            )

            tvItemNbaGameTeamsVisitorTeamFullName.text = nbaGame.visitorTeamRemote.fullName
        }

    }
}