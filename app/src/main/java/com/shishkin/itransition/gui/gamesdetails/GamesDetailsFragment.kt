package com.shishkin.itransition.gui.gamesdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.shishkin.itransition.databinding.FragmentGamesDetailsBinding
import com.shishkin.itransition.network.entities.NbaGame
import dagger.android.support.DaggerFragment
import java.text.SimpleDateFormat
import java.util.*


class GamesDetailsFragment : DaggerFragment() {

    private var _binding: FragmentGamesDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGamesDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val nbaGame: NbaGame? = arguments?.getParcelable("nbaGame")

        with(binding){
            tvGameSeason.text ="Season: " + nbaGame?.season.toString()
            tvGameStatus.text ="Status: " + nbaGame?.status.toString()
            tvGameDate.text = "Date: " + nbaGame?.date?.let { convertDate(it) }

            tvHomeTeamName.text ="Name: " + nbaGame?.homeTeam?.name
            tvHomeTeamCity.text ="City: " + nbaGame?.homeTeam?.city
            tvHomeTeamAbbreviation.text ="Abbreviation: " +  nbaGame?.homeTeam?.abbreviation
            tvHomeTeamFullName.text = nbaGame?.homeTeam?.fullName
            tvHomeTeamDivision.text = nbaGame?.homeTeam?.division
            tvHomeTeamConference.text = nbaGame?.homeTeam?.conference
            tvHomeTeamScore.text ="Home team score: "+ nbaGame?.homeTeam?.home_team_score.toString()

            tvVisitorTeamName.text ="Name: " + nbaGame?.visitorTeam?.name
            tvVisitorTeamCity.text ="City: " + nbaGame?.visitorTeam?.city
            tvVisitorTeamAbbreviation.text = "Abbreviation: " + nbaGame?.visitorTeam?.abbreviation
            tvVisitorTeamFullName.text = nbaGame?.visitorTeam?.fullName
            tvVisitorTeamDivision.text = nbaGame?.visitorTeam?.division
            tvVisitorTeamConference.text = nbaGame?.visitorTeam?.conference
            tvVisitorTeamScore.text = "Visitor team score: " + nbaGame?.visitorTeam?.home_team_score.toString()

        }

    }

    private fun convertDate(date: Date): String? {
        val DATE_PATTERN = "yyyy-MM-dd"
        val sdf = SimpleDateFormat(DATE_PATTERN)
        return sdf.format(date)
    }

}