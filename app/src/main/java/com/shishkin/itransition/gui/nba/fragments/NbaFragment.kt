package com.shishkin.itransition.gui.nba.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.shishkin.itransition.R

class NbaFragment : Fragment() {
    lateinit var button: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_nba, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        button = view.findViewById(R.id.btnGoToNbaPlayerInfo)
        button.setOnClickListener {
            findNavController().navigate(R.id.action_nbaFragment_to_nbaDetailsFragment)
        }
    }

}