package com.shishkin.itransition.gui.gamesdetails

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.shishkin.itransition.databinding.FragmentGamesDetailsBinding
import com.shishkin.itransition.network.entities.NbaGame
import dagger.android.support.DaggerFragment


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
        Log.d("GameDetails", nbaGame?.homeTeamScore.toString())

    }

}