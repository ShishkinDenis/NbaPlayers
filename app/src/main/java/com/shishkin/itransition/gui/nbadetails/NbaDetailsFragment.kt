package com.shishkin.itransition.gui.nbadetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.shishkin.itransition.R
import com.shishkin.itransition.databinding.FragmentNbaDetailsBinding
import dagger.android.support.DaggerFragment
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class NbaDetailsFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: NbaDetailsViewModelFactory
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
        val viewModel: NbaDetailsViewModel by viewModels { viewModelFactory }

        lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.specificPlayerRemoteState.collectLatest { uiState ->
                    uiState.fold(
                        onLoading = {
                            Timber.tag("Retrofit").d( "NbaDetailsFragment: Loading")
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
                            Timber.tag("Retrofit").d( "NbaDeytailsFragment: Error: " + message)
                        }
                    )
                }
            }
        }
    }
}