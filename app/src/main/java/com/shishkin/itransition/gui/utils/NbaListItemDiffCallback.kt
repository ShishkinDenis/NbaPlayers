package com.shishkin.itransition.gui.utils

import androidx.recyclerview.widget.DiffUtil
import com.shishkin.itransition.gui.nba.uientities.NbaPlayerUi
import com.shishkin.itransition.gui.nba.uientities.NbaTeamUi

class NbaListItemDiffCallback : DiffUtil.ItemCallback<ListItem>() {

    override fun areItemsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
        return (oldItem.item as? NbaPlayerUi)?.id == (newItem.item as? NbaPlayerUi)?.id ||
                (oldItem.item as? NbaTeamUi)?.id == (newItem.item as? NbaTeamUi)?.id
    }

    override fun areContentsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
        return oldItem == newItem
    }
}
