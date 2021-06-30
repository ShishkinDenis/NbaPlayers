package com.shishkin.itransition.gui.nba

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.shishkin.itransition.R
import com.shishkin.itransition.gui.utils.NbaListItemDiffCallback
import com.shishkin.itransition.network.entities.ListItem
import com.shishkin.itransition.network.entities.NbaPlayer
import com.shishkin.itransition.network.entities.NbaTeam

class NbaPlayersListAdapter(private val listener: NbaPlayerItemListener) :
        ListAdapter<ListItem, RecyclerView.ViewHolder>(NbaListItemDiffCallback()) {

    // TODO Evgeny: если есть companion object, то его располоагать надо снизу класса,
    // Идут: паблик филды, затчем init {} , затем абстракные методы (если абстрактные класс),
    // затем методы, затем companion в самом низу
    companion object {
        const val VIEW_TYPE_NBA_PLAYER = 1
        const val VIEW_TYPE_NBA_TEAM = 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_NBA_PLAYER) {
            // TODO Evgeny Для этого inflate можно сделать Kotlin Extension, чтобы было:
                // parent.inflateLayout(R.layout.nba_player_adapter, false)
            val view: View =
                    LayoutInflater.from(parent.context)
                            .inflate(R.layout.nba_player_adapter, parent, false)
            // TODO Evgeny Что за наркоманское форматирование? :)
            NbaPlayerViewHolder(
                    view, listener
            )
        } else {
            val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.nba_player_team_adapter, parent, false)
              // TODO Evgeny Что за наркоманское форматирование? :)
            TeamViewHolder(
                    view
            )
        }
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position)?.viewType ?: VIEW_TYPE_NBA_PLAYER
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItem(position)?.viewType == VIEW_TYPE_NBA_PLAYER) {
            // TODO Evgeny: as не тайп-сэйфти. Если будет null, то после as все крашнется.
                // Должно быть as?
            val nbaPlayer: NbaPlayer = getItem(position)?.item as NbaPlayer

            // TODO Evgeny: текст в коде - фу, плохо. Все текста всегда выносить в strings.xml

            // TODO Evgeny и вообще весь этот код надо вынести в NbaPlayerViewHolder.bind()
            val nbaPlayerName: String =
                    "NBA player: " + nbaPlayer.firstName + " " + nbaPlayer.lastName
            val nbaPlayerPosition: String = "Position: " + nbaPlayer.position
            (holder as NbaPlayerViewHolder).nbaPlayerName.text = nbaPlayerName
            holder.nbaPlayerPosition.text = nbaPlayerPosition
            nbaPlayer.let { holder.getNbaItem(it) }
        } else {
            val nbaTeam: NbaTeam = (getItem(position)?.item as NbaTeam)
            val teamFullName: String = "Team: " + nbaTeam.fullName + ", " + nbaTeam.abbreviation
            val nbaTeamCity: String = "City: " + nbaTeam.city
            (holder as TeamViewHolder).teamFullName.text = teamFullName
            holder.nbaTeamCity.text = nbaTeamCity
        }
    }
}



