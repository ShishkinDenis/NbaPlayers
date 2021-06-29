package com.shishkin.itransition.gui.nba

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.shishkin.itransition.R
import com.shishkin.itransition.network.entities.NbaPlayer

class NbaPlayerViewHolder(itemView: View, private val listener: NbaPlayerItemListener) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

    private lateinit var nbaPlayer: NbaPlayer

    // TODO   view/data binding
    val nbaPlayerName: TextView = itemView.findViewById(R.id.tv_name)
    val nbaPlayerPosition: TextView = itemView.findViewById(R.id.tv_position)

    fun getNbaItem(item: NbaPlayer) {
        this.nbaPlayer = item
    }

    init {
        itemView.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        listener.onClickedNbaPlayer(nbaPlayer.id)
    }

}