package com.shishkin.itransition.gui.nba.lists.withoutpagination

import androidx.recyclerview.widget.DiffUtil
import com.shishkin.itransition.gui.nba.lists.ListItem

class NbaPlayerItemDiffCallback : DiffUtil.ItemCallback<ListItem>() {

    override fun areItemsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
        return oldItem == newItem
    }

}