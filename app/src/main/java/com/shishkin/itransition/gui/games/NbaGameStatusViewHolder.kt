package com.shishkin.itransition.gui.games

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.shishkin.itransition.R
import com.shishkin.itransition.databinding.ItemNbaGameStatusBinding
import com.shishkin.itransition.gui.utils.ListItem
import com.shishkin.itransition.network.entities.NbaGame
import java.text.SimpleDateFormat
import java.util.*

private const val NBA_DATE_FORMAT = "yyyy-MM-dd"

class NbaGameStatusViewHolder(
    val binding: ItemNbaGameStatusBinding,
    private val listener: NbaGameItemListener
) :
    RecyclerView.ViewHolder(binding.root), View.OnClickListener {

    private var nbaGame: NbaGame? = null

    init {
        itemView.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        listener.onClickedNbaGame(nbaGame)
    }

    fun bind(item: ListItem) {
        nbaGame = item.item as? NbaGame
        with(binding){
            tvItemNbaGameStatusGameSeason.text =
                binding.root.context.getString(R.string.nba_game_season, nbaGame?.season)
            tvItemNbaGameStatusGameStatus.text =
                binding.root.context.getString(R.string.nba_game_status, nbaGame?.status)
            tvItemNbaGameStatusGameDate.text = binding.root.context.getString(
                R.string.nba_game_date, nbaGame?.date?.let { date -> convertDate(date) })
            tvItemNbaGameStatusHomeTeamScore.text = binding.root.context.getString(
                R.string.nba_game_home_team_score, nbaGame?.homeTeamScore)
        }
    }

    private fun convertDate(date: Date): String? {
        val sdf = SimpleDateFormat(NBA_DATE_FORMAT, Locale.US)
        return sdf.format(date)
    }
}