package com.shishkin.itransition.gui.nba

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.shishkin.itransition.R
import com.shishkin.itransition.databinding.ItemNbaPlayerBinding
import com.shishkin.itransition.gui.nba.uientities.NbaPlayerUi

class NbaPlayerViewHolder(
    private val binding: ItemNbaPlayerBinding,
    private val listener: NbaPlayerItemListener
) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {

    private var nbaPlayerUi: NbaPlayerUi? = null

    init {
        binding.root.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        nbaPlayerUi?.id?.let(listener::onClickedNbaPlayer)
    }

    fun bind(nbaPlayerUi: NbaPlayerUi) {
        this.nbaPlayerUi = nbaPlayerUi
        binding.tvItemNbaPlayerName.text = itemView.context.getString(
            R.string.nba_player_name, nbaPlayerUi.firstName, nbaPlayerUi.lastName
        )
        binding.tvItemNbaPlayerPosition.text =
            itemView.context.getString(R.string.nba_player_position, nbaPlayerUi.position)
    }
}