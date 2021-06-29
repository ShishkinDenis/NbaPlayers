package com.shishkin.itransition.gui.utils

import androidx.recyclerview.widget.DiffUtil
import com.shishkin.itransition.network.entities.ListItem

class NbaListItemDiffCallback : DiffUtil.ItemCallback<ListItem>() {

    override fun areItemsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
        return oldItem == newItem
    }

}
