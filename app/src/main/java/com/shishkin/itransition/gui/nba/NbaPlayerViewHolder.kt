package com.shishkin.itransition.gui.nba

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.shishkin.itransition.R
import com.shishkin.itransition.databinding.ItemNbaPlayerBinding
import com.shishkin.itransition.network.entities.NbaPlayerRemote

class NbaPlayerViewHolder(
    private val binding: ItemNbaPlayerBinding,
    private val listener: NbaPlayerItemListener
) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {

    private var nbaPlayerRemote: NbaPlayerRemote? = null

    init {
        binding.root.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        nbaPlayerRemote?.id?.let(listener::onClickedNbaPlayer)
    }

    fun bind(nbaPlayerRemote: NbaPlayerRemote) {
        this.nbaPlayerRemote = nbaPlayerRemote
        binding.tvItemNbaPlayerName.text = itemView.context.getString(
            R.string.nba_player_name, nbaPlayerRemote.firstName, nbaPlayerRemote.lastName
        )
        binding.tvItemNbaPlayerPosition.text =
            itemView.context.getString(R.string.nba_player_position, nbaPlayerRemote.position)
    }
}