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
            tvGameDate.text = "Date: " + nbaGame?.date?.let { convertDate(it) }

            tvHomeTeamCity.text ="City: " + nbaGame?.homeTeam?.city
            tvHomeTeamFullName.text =nbaGame?.homeTeam?.fullName
            tvHomeTeamDivision.text ="Division: " +  nbaGame?.homeTeam?.division


            tvVisitorTeamCity.text ="City: " + nbaGame?.visitorTeam?.city
            tvVisitorTeamFullName.text =nbaGame?.visitorTeam?.fullName
            tvVisitorTeamDivision.text ="Division: " +  nbaGame?.visitorTeam?.division
        }

    }

    private fun convertDate(date: Date): String? {
        val DATE_PATTERN = "yyyy-MM-dd"
        val sdf = SimpleDateFormat(DATE_PATTERN)
        return sdf.format(date)
    }

}