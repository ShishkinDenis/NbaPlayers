package com.shishkin.itransition.gui.games

import androidx.recyclerview.widget.RecyclerView
import com.shishkin.itransition.R
import com.shishkin.itransition.databinding.ItemNbaGameTeamsBinding
import com.shishkin.itransition.gui.utils.ListItem
import com.shishkin.itransition.network.entities.NbaGame

class NbaGameTeamsViewHolder(val binding: ItemNbaGameTeamsBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: ListItem) {
        val nbaGame: NbaGame? = item.item as? NbaGame
        with(binding){
            tvItemNbaGameTeamsHomeTeamName.text = binding.root.context.getString(
                R.string.nba_game_home_team_name, nbaGame?.homeTeam?.name)

            tvItemNbaGameTeamsHomeTeamCity.text = binding.root.context.getString(
                R.string.nba_game_home_team_city, nbaGame?.homeTeam?.city)

            tvItemNbaGameTeamsHomeTeamAbbreviation.text = binding.root.context.getString(
                R.string.nba_game_home_team_abbreviation, nbaGame?.homeTeam?.abbreviation)

            tvItemNbaGameTeamsHomeTeamFullName.text = nbaGame?.homeTeam?.fullName

            tvItemNbaGameTeamsVisitorTeamName.text = binding.root.context.getString(
                R.string.nba_game_visitor_team_name, nbaGame?.visitorTeam?.name)

            tvItemNbaGamesTeamsVisitorTeamCity.text = binding.root.context.getString(
                R.string.nba_game_visitor_team_city, nbaGame?.visitorTeam?.city)

            tvItemNbaGameTeamsVisitorTeamAbbreviation.text = binding.root.context.getString(
                R.string.nba_game_visitor_team_abbreviation, nbaGame?.visitorTeam?.abbreviation)

            tvItemNbaGameTeamsVisitorTeamFullName.text = nbaGame?.visitorTeam?.fullName
        }

    }
}