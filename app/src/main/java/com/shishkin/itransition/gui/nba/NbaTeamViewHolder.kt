package com.shishkin.itransition.gui.nba

import androidx.recyclerview.widget.RecyclerView
import com.shishkin.itransition.R
import com.shishkin.itransition.databinding.ItemNbaTeamBinding
import com.shishkin.itransition.gui.utils.ListItem
import com.shishkin.itransition.network.entities.NbaTeam

class NbaTeamViewHolder(val binding: ItemNbaTeamBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: ListItem) {
        val nbaTeam = item.item as? NbaTeam
        binding.tvItemNbaTeamFullName.text = binding.root.context.getString(
            R.string.nba_team_full_name, nbaTeam?.fullName, nbaTeam?.abbreviation)
        binding.tvItemNbaTeamCity.text =
            binding.root.context.getString(R.string.nba_team_city, nbaTeam?.city)
    }
}