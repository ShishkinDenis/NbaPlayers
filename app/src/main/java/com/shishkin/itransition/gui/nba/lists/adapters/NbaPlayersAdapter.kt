package com.shishkin.itransition.gui.nba.lists.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.shishkin.itransition.R
import com.shishkin.itransition.network.entities.NbaPlayer


class NbaPlayersAdapter(private val playersList: List<NbaPlayer>?) :
    RecyclerView.Adapter<NbaPlayersAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_main_article_adapter, parent, false)
        return NbaPlayersAdapter.ViewHolder(view)
    }

    override fun getItemCount(): Int {
//  TODO    !! remove
        return playersList?.size!!
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.titleText.text = playersList?.get(position)?.firstName
    }

    //    TODO move to another file
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleText: TextView = itemView.findViewById(R.id.nba_player_adapter_tv_title)
//            val descriptionText: TextView? = itemView.findViewById(R.id.article_adapter_tv_description)
    }
}