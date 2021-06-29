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
import dagger.android.support.DaggerFragment
import kotlinx.coroutines.flow.collectLatest
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
        _binding = FragmentNbaDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        nbaDetailsViewModel =
                ViewModelProviders.of(this, nbaDetailsViewModelFactory)
                        .get(NbaDetailsViewModel::class.java)

        lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                nbaDetailsViewModel.specificPlayerState.collectLatest { uiState ->
                    uiState.fold(
                            onLoading = {
                                Log.d("Retrofit", "NbaFragment: Loading")
                            },
                            onSuccess = {
                                binding.tvSpecificNbaPlayerName.text =
                                        "Name: " + uiState.data?.firstName + " " + uiState.data?.lastName
                                binding.tvSpecificNbaPlayerTeam.text =
                                        "Team: " + uiState.data?.team?.abbreviation
                                binding.tvSpecificNbaPlayerPosition.text =
                                        "Position: " + uiState.data?.position

//                            TODO Why three following textView are null?
                                binding.tvSpecificNbaPlayerHeightFeet.text =
                                        "Height feet: " + uiState.data?.heightFeet.toString()
                                binding.tvSpecificNbaPlayerHeightInches.text =
                                        "Height inches: " + uiState.data?.heightInches.toString()
                                binding.tvSpecificNbaPlayerWeightPounds.text =
                                        "Weight pounds: " + uiState.data?.weightPounds.toString()
                            },
                            onError = { throwable, message ->
                                Log.d("Retrofit", "NbaFragment: Error: " + message)
                            }
                    )
                }
            }
        }
    }

}