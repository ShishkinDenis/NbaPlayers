package com.shishkin.itransition.gui.nba

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.shishkin.itransition.databinding.ItemNbaPlayerBinding
import com.shishkin.itransition.databinding.ItemNbaTeamBinding
import com.shishkin.itransition.gui.nba.uientities.NbaPlayerUi
import com.shishkin.itransition.gui.nba.uientities.NbaTeamUi
import com.shishkin.itransition.gui.utils.ListItem
import com.shishkin.itransition.gui.utils.NbaListItemDiffCallback

const val VIEW_TYPE_NBA_PLAYER = 1
const val VIEW_TYPE_NBA_TEAM = 2

class NbaPlayersListAdapter(private val listener: NbaPlayerItemListener) :
    ListAdapter<ListItem, RecyclerView.ViewHolder>(NbaListItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_NBA_PLAYER) {
            val binding =
                ItemNbaPlayerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            NbaPlayerViewHolder(binding, listener)
        } else {
            val binding =
                ItemNbaTeamBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            NbaTeamViewHolder(binding)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position)?.viewType ?: VIEW_TYPE_NBA_PLAYER
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItem(position)?.viewType == VIEW_TYPE_NBA_PLAYER) {
            (getItem(position)?.item as? NbaPlayerUi)?.let { nbaPlayerUi ->
                (holder as? NbaPlayerViewHolder)?.bind(nbaPlayerUi)
            }
        } else {
            (getItem(position)?.item as? NbaTeamUi)?.let { nbaTeamUi ->
                (holder as? NbaTeamViewHolder)?.bind(nbaTeamUi)
            }
        }
    }
}



