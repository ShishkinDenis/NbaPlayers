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
import com.shishkin.itransition.databinding.FragmentNbaDetailsBinding
import com.shishkin.itransition.gui.nba.NbaPlayerUiState
import dagger.android.support.DaggerFragment
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


class NbaDetailsFragment : DaggerFragment() {

    @Inject
    lateinit var nbaDetailsViewModelFactory: NbaDetailsViewModelFactory
    lateinit var nbaDetailsViewModel: NbaDetailsViewModel

    private var _binding: FragmentNbaDetailsBinding? = null
    private val binding get() = _binding!!



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        return inflater.inflate(R.layout.fragment_nba_details, container, false)
        _binding = FragmentNbaDetailsBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        nbaDetailsViewModel =
            ViewModelProviders.of(this, nbaDetailsViewModelFactory).get(NbaDetailsViewModel::class.java)
        val nbaPlayerId : Int? = arguments?.getInt("id")

        Log.d("Retrofit", "Id : $nbaPlayerId")

        lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                nbaDetailsViewModel.uiState.collect { uiState ->
                    when (uiState) {
                        is NbaPlayerUiState.Success -> {
//                            TODO move to function
                            Log.d("Retrofit", "NbaDetailsFragment: Success  " + uiState.nbaPlayer?.firstName.toString())
                            Log.d("Retrofit", "NbaDetailsFragment: Success  " + uiState.nbaPlayer?.heightFeet.toString())
                            binding.tvSpecificNbaPlayerName.text ="Name: " + uiState.nbaPlayer?.firstName + " " + uiState.nbaPlayer?.lastName
                            binding.tvSpecificNbaPlayerTeam.text = "Team: "+ uiState.nbaPlayer?.team?.abbreviation
                            binding.tvSpecificNbaPlayerPosition.text ="Position: " + uiState.nbaPlayer?.position
                            binding.tvSpecificNbaPlayerHeightFeet.text ="Height feet: " + uiState.nbaPlayer?.heightFeet.toString()
                            binding.tvSpecificNbaPlayerHeightInches.text ="Height inches: " + uiState.nbaPlayer?.heightInches.toString()
                            binding.tvSpecificNbaPlayerWeightPounds.text ="Weight pounds: " + uiState.nbaPlayer?.weightPounds.toString()
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