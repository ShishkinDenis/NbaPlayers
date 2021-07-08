package com.shishkin.itransition.gui.nba

import androidx.recyclerview.widget.RecyclerView
import com.shishkin.itransition.R
import com.shishkin.itransition.databinding.ItemNbaTeamBinding
import com.shishkin.itransition.network.entities.NbaTeamRemote

class NbaTeamViewHolder(private val binding: ItemNbaTeamBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(nbaTeamRemote: NbaTeamRemote) {
        with(binding) {
            tvItemNbaTeamFullName.text = binding.root.context.getString(
                R.string.nba_team_full_name, nbaTeamRemote.fullName, nbaTeamRemote.abbreviation
            )
            tvItemNbaTeamCity.text =
                binding.root.context.getString(R.string.nba_team_city, nbaTeamRemote.city)
        }

    }
}