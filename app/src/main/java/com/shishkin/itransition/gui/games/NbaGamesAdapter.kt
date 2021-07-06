package com.shishkin.itransition.gui.games

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.shishkin.itransition.databinding.ItemNbaGameStatusBinding
import com.shishkin.itransition.databinding.ItemNbaGameTeamsBinding
import com.shishkin.itransition.gui.utils.ListItem
import com.shishkin.itransition.gui.utils.NbaListItemDiffCallback

const val VIEW_TYPE_NBA_GAME = 1
const val VIEW_TYPE_NBA_GAME_TEAM = 2

class NbaGamesAdapter(private val listener: NbaGameItemListener) :
    PagingDataAdapter<ListItem, RecyclerView.ViewHolder>(NbaListItemDiffCallback()) {

    override fun getItemViewType(position: Int): Int {
        return getItem(position)?.viewType ?: VIEW_TYPE_NBA_GAME
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_NBA_GAME) {
            val binding = ItemNbaGameStatusBinding.inflate(
                LayoutInflater.from(parent.context), parent, false)
            NbaGameStatusViewHolder(binding, listener)
        } else {
            val binding = ItemNbaGameTeamsBinding.inflate(
                LayoutInflater.from(parent.context), parent, false)
            NbaGameTeamsViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItem(position)?.viewType == VIEW_TYPE_NBA_GAME) {
            getItem(position)?.let { (holder as? NbaGameStatusViewHolder)?.bind(it) }
        } else {
            getItem(position)?.let { (holder as? NbaGameTeamsViewHolder)?.bind(it) }
        }
    }
}




