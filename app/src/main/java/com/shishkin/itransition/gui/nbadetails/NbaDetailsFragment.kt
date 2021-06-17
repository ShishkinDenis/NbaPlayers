package com.shishkin.itransition.gui.nbadetails

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.shishkin.itransition.R
import com.shishkin.itransition.gui.nba.NbaPlayerUiState
import com.shishkin.itransition.gui.nba.NbaPlayersUiState
import com.shishkin.itransition.gui.nba.lists.NbaPlayersAdapter
import com.shishkin.itransition.utils.MyViewModelFactory
import dagger.android.support.DaggerFragment
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


class NbaDetailsFragment : DaggerFragment() {

    @Inject
    lateinit var myViewModelFactory: MyViewModelFactory
var nbaPlayerId = arguments?.getInt("id")
//    lateinit var myViewModelFactory: MyViewModelFactory = MyViewModelFactory()
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

        Log.d("Retrofit", "Id : " + nbaPlayerId.toString())

        lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                nbaDetailsViewModel.uiState.collect { uiState ->
                    when (uiState) {
                        is NbaPlayerUiState.Success -> {
                            Log.d("Retrofit", "NbaDetailsFragment: " + uiState.nbaPlayer?.firstName.toString())
                        }
                        is NbaPlayerUiState.Error -> {
                        }
                        is NbaPlayerUiState.Loading -> {
                        }
                        is NbaPlayerUiState.Empty -> {
                        }
                    }
                }
            }
        }
    }


}