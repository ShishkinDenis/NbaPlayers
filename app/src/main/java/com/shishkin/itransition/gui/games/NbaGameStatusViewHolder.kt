package com.shishkin.itransition.gui.games

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.shishkin.itransition.R
import com.shishkin.itransition.databinding.ItemNbaGameStatusBinding
import com.shishkin.itransition.network.entities.NbaGame
import java.text.SimpleDateFormat
import java.util.*

private const val NBA_DATE_FORMAT = "yyyy-MM-dd"

class NbaGameStatusViewHolder(
    private val binding: ItemNbaGameStatusBinding,
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

    fun bind(nbaGame: NbaGame) {
        with(binding) {
            val context = root.context
            tvItemNbaGameStatusGameSeason.text =
                context.getString(R.string.nba_game_season, nbaGame.season)
            tvItemNbaGameStatusGameStatus.text =
                context.getString(R.string.nba_game_status, nbaGame.status)
            tvItemNbaGameStatusGameDate.text = context.getString(
                R.string.nba_game_date, convertDate(nbaGame.date)
            )
            tvItemNbaGameStatusHomeTeamScore.text = context.getString(
                R.string.nba_game_home_team_score, nbaGame.homeTeamScore
            )
        }
    }

    private fun convertDate(date: Date): String? {
        val sdf = SimpleDateFormat(NBA_DATE_FORMAT, Locale.US)
        return sdf.format(date)
    }
}