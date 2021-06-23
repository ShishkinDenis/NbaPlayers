package com.shishkin.itransition.gui.nba.lists.pagination

import androidx.recyclerview.widget.DiffUtil
import com.shishkin.itransition.gui.nba.lists.ListItem
import com.shishkin.itransition.network.entities.NbaPlayer


class NbaPlayerItemPaginationDiffCallback : DiffUtil.ItemCallback<NbaPlayer>() {

    override fun areItemsTheSame(oldItem: NbaPlayer, newItem: NbaPlayer): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: NbaPlayer, newItem: NbaPlayer): Boolean {
        return oldItem == newItem
    }

}


