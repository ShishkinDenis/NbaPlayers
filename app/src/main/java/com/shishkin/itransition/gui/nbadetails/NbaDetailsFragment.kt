package com.shishkin.itransition.gui.nbadetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.shishkin.itransition.R
import com.shishkin.itransition.di.MyApplication
import javax.inject.Inject


class NbaDetailsFragment : Fragment() {

    @Inject
    lateinit var nbaDetailsViewModel: NbaDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_nba_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        MyApplication.appComponent.inject(this)
        nbaDetailsViewModel.fetchSpecificNbaPlayer(7)
    }


}