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
import com.shishkin.itransition.utils.MyViewModelFactory
import dagger.android.support.DaggerFragment
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


class NbaDetailsFragment : DaggerFragment() {

    @Inject
    lateinit var myViewModelFactory: MyViewModelFactory
//    var nbaPlayerId = requireArguments().getInt("id")
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
        val nbaPlayerId : Int? = arguments?.getInt("id")

        Log.d("Retrofit", "Id : $nbaPlayerId")

        lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                nbaDetailsViewModel.uiState.collect { uiState ->
                    when (uiState) {
                        is NbaPlayerUiState.Success -> {
                            Log.d("Retrofit", "NbaDetailsFragment: Success  " + uiState.nbaPlayer?.firstName.toString())
                        }
                        is NbaPlayerUiState.Error -> {
                            Log.d("Retrofit", "NbaDetailsFragment: Error")
                        }
                        is NbaPlayerUiState.Loading -> {
                            Log.d("Retrofit", "NbaDetailsFragment: Loading")
                        }
                        is NbaPlayerUiState.Empty -> {
                            Log.d("Retrofit", "NbaDetailsFragment: Empty")
                        }
                    }
                }
            }
        }
    }


}