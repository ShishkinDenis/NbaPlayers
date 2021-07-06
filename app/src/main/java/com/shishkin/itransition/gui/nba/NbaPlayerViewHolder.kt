package com.shishkin.itransition.gui.nba

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.shishkin.itransition.R
import com.shishkin.itransition.databinding.ItemNbaPlayerBinding
import com.shishkin.itransition.gui.utils.ListItem
import com.shishkin.itransition.network.entities.NbaPlayer

class NbaPlayerViewHolder(
    val binding: ItemNbaPlayerBinding,
    private val listener: NbaPlayerItemListener
) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {

    private var nbaPlayer: NbaPlayer? = null

    init {
        binding.root.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        nbaPlayer?.id?.let(listener::onClickedNbaPlayer)
    }

    fun bind(item: ListItem) {
        nbaPlayer = item.item as? NbaPlayer
        binding.tvItemNbaPlayerName.text = itemView.context.getString(
            R.string.nba_player_name, nbaPlayer?.firstName, nbaPlayer?.lastName)
        binding.tvItemNbaPlayerPosition.text =
            itemView.context.getString(R.string.nba_player_position, nbaPlayer?.position)
    }
}