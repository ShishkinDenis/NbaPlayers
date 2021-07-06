package com.shishkin.itransition.gui.gamesdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.shishkin.itransition.R
import com.shishkin.itransition.databinding.FragmentGamesDetailsBinding
import com.shishkin.itransition.network.entities.NbaGame
import dagger.android.support.DaggerFragment
import java.text.SimpleDateFormat
import java.util.*

private const val NBA_DATE_FORMAT = "yyyy-MM-dd"

class GamesDetailsFragment : DaggerFragment() {

    private lateinit var _binding: FragmentGamesDetailsBinding
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGamesDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val nbaGame: NbaGame? = arguments?.getParcelable(getString(R.string.arg_nba_game))

        with(binding) {
            tvGameDetailsGameSeason.text = getString(R.string.games_details_season, nbaGame?.season)

            tvGameDetailsGameDate.text = getString(R.string.games_details_date,
                nbaGame?.date?.let { date -> convertDate(date) })
            tvGameDetailsHomeTeamCity.text = getString(
                R.string.games_details_home_team_city, nbaGame?.homeTeam?.city)

            tvGameDetailsHomeTeamFullName.text = nbaGame?.homeTeam?.fullName

            tvGameDetailsHomeTeamDivision.text =
                getString(R.string.games_details_home_team_division, nbaGame?.homeTeam?.division)

            tvGameDetailsVisitorTeamCity.text =
                getString(R.string.games_details_visitor_team_city, nbaGame?.visitorTeam?.city)

            tvGameDetailsVisitorTeamFullName.text = nbaGame?.visitorTeam?.fullName

            tvGameDetailsVisitorTeamDivision.text = getString(
                R.string.games_details_visitor_team_division, nbaGame?.visitorTeam?.division)
        }
    }

    private fun convertDate(date: Date): String? {
        val sdf = SimpleDateFormat(NBA_DATE_FORMAT, Locale.US)
        return sdf.format(date)
    }
}