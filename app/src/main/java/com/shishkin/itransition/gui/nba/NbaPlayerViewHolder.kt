package com.shishkin.itransition.gui.nba

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.shishkin.itransition.R
import com.shishkin.itransition.gui.utils.ListItem
import com.shishkin.itransition.network.entities.NbaPlayer

class NbaPlayerViewHolder(itemView: View, private val listener: NbaPlayerItemListener)
    : RecyclerView.ViewHolder(itemView), View.OnClickListener {

    private var nbaPlayer: NbaPlayer? = null

    // TODO Evgeny все верно, тут использовать ViewBinding
    val nbaPlayerName: TextView = itemView.findViewById(R.id.tv_name)
    val nbaPlayerPosition: TextView = itemView.findViewById(R.id.tv_position)

    init {
        itemView.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        nbaPlayer?.id?.let { listener.onClickedNbaPlayer(it) }
    }
    fun bind(item: ListItem){
        nbaPlayer = item.item as? NbaPlayer
            val nbaPlayerName: String =
                itemView.context.getString(R.string.nba_player_name,nbaPlayer?.firstName, nbaPlayer?.lastName)
            val nbaPlayerPosition: String =
                itemView.context.getString(R.string.nba_player_position,nbaPlayer?.position)
//        TODO убрать this, применить viewBinding
            this.nbaPlayerName.text = nbaPlayerName
            this.nbaPlayerPosition.text = nbaPlayerPosition
    }
}