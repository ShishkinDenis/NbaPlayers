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
                viewModel.specificPlayerState.collectLatest { uiState ->
                    uiState.fold(
                        onLoading = {
                            Log.d("Retrofit", "NbaFragment: Loading")
                        },
                        onSuccess = {
                            binding.tvSpecificNbaPlayerName.text =
                                getString(R.string.nba_details_name, it?.firstName, it?.lastName)
                            binding.tvSpecificNbaPlayerTeam.text =
                                getString(R.string.nba_details_team, it?.team?.abbreviation)
                            binding.tvSpecificNbaPlayerPosition.text =
                                getString(R.string.nba_details_position, it?.position)

                            binding.tvSpecificNbaPlayerHeightFeet.text =
                                getString(R.string.nba_details_height_feet, it?.heightFeet)
                            binding.tvSpecificNbaPlayerHeightInches.text =
                                getString(R.string.nba_details_height_inches, it?.heightInches)
                            binding.tvSpecificNbaPlayerWeightPounds.text =
                                getString(R.string.nba_details_weight_pounds, it?.weightPounds)
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