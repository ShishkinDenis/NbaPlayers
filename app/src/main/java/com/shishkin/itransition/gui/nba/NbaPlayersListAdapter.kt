package com.shishkin.itransition.gui.nba

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.shishkin.itransition.R
import com.shishkin.itransition.gui.utils.ListItem
import com.shishkin.itransition.gui.utils.NbaListItemDiffCallback

const val VIEW_TYPE_NBA_PLAYER = 1
const val VIEW_TYPE_NBA_TEAM = 2

class NbaPlayersListAdapter(private val listener: NbaPlayerItemListener) :
    ListAdapter<ListItem, RecyclerView.ViewHolder>(NbaListItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_NBA_PLAYER) {
            // TODO Evgeny Для этого inflate можно сделать Kotlin Extension, чтобы было:
            // parent.inflateLayout(R.layout.nba_player_adapter, false)
            val view: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.nba_player_adapter, parent, false)
            NbaPlayerViewHolder(view, listener)
        } else {
            val view: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.nba_player_team_adapter, parent, false)
            NbaTeamViewHolder(view)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position)?.viewType ?: VIEW_TYPE_NBA_PLAYER
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItem(position)?.viewType == VIEW_TYPE_NBA_PLAYER) {
            (holder as? NbaPlayerViewHolder)?.bind(getItem(position))
        } else {
            (holder as? NbaTeamViewHolder)?.bind(getItem(position))
        }
    }
}



