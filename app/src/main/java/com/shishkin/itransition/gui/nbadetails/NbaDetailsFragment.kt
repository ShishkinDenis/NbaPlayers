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
import com.shishkin.itransition.databinding.FragmentNbaDetailsBinding
import dagger.android.support.DaggerFragment
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class NbaDetailsFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: NbaDetailsViewModelFactory
    private lateinit var viewModel: NbaDetailsViewModel
    private lateinit var _binding: FragmentNbaDetailsBinding
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNbaDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // TODO Evgeny: для получения viewModel ты можешь использовать специальный extension:
        //  val nbaDetailsViewModel: NbaDetailsViewModel by viewModels { nbaDetailsViewModelFactory }
        viewModel =
            ViewModelProviders.of(this, viewModelFactory)
                .get(NbaDetailsViewModel::class.java)

        lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.specificPlayerRemoteState.collectLatest { uiState ->
                    uiState.fold(
                        onLoading = {
                            Log.d("Retrofit", "NbaFragment: Loading")
                        },
                        onSuccess = { nbaPlayer ->
                            with(binding) {
                                tvNbaDetailsName.text =
                                    getString(
                                        R.string.nba_details_name, nbaPlayer?.firstName,
                                        nbaPlayer?.lastName
                                    )
                                tvNbaDetailsTeam.text =
                                    getString(
                                        R.string.nba_details_team,
                                        nbaPlayer?.team?.abbreviation
                                    )
                                tvNbaDetailsPosition.text =
                                    getString(R.string.nba_details_position, nbaPlayer?.position)

                                tvNbaDetailsHeightFeet.text =
                                    getString(
                                        R.string.nba_details_height_feet,
                                        nbaPlayer?.heightFeet
                                    )
                                tvNbaDetailsHeightInches.text =
                                    getString(
                                        R.string.nba_details_height_inches,
                                        nbaPlayer?.heightInches
                                    )
                                tvNbaDetailsWeightPounds.text =
                                    getString(
                                        R.string.nba_details_weight_pounds,
                                        nbaPlayer?.weightPounds
                                    )
                            }
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