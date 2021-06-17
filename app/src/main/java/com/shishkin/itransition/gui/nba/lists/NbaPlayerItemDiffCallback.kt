package com.shishkin.itransition.gui.nba.lists

import androidx.recyclerview.widget.DiffUtil
import com.shishkin.itransition.network.entities.NbaPlayer

class NbaPlayerItemDiffCallback : DiffUtil.ItemCallback<NbaPlayer>() {

    override fun areItemsTheSame(oldItem: NbaPlayer, newItem: NbaPlayer): Boolean = oldItem == newItem

    override fun areContentsTheSame(oldItem: NbaPlayer, newItem: NbaPlayer): Boolean = oldItem == newItem

}
