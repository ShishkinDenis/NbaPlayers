package com.shishkin.itransition.gui.nbadetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.shishkin.itransition.R
import com.shishkin.itransition.utils.MyViewModelFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject


class NbaDetailsFragment : DaggerFragment() {

    @Inject
    lateinit var myViewModelFactory: MyViewModelFactory
    lateinit var nbaDetailsViewModel: NbaDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_nba_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        nbaDetailsViewModel =
            ViewModelProviders.of(this, myViewModelFactory).get(NbaDetailsViewModel::class.java)

        nbaDetailsViewModel.fetchSpecificNbaPlayer(7)
    }


}