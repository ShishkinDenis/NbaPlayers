package com.shishkin.itransition.gui.nba

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.shishkin.itransition.R
import com.shishkin.itransition.databinding.FragmentNbaBinding
import com.shishkin.itransition.gui.nba.mappers.NbaPlayerUiToListItemMapper
import com.shishkin.itransition.gui.utils.CustomViewTypeItemDecoration
import dagger.android.support.DaggerFragment
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class NbaFragment : DaggerFragment(), NbaPlayerItemListener {

    @Inject
    lateinit var viewModelFactory: NbaViewModelFactory
    private lateinit var viewModel: NbaViewModel
    private lateinit var nbaPlayersListAdapter: NbaPlayersListAdapter
    private lateinit var _binding: FragmentNbaBinding
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNbaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(NbaViewModel::class.java)
        initNbaPlayersRecyclerView()

        lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.playersState.collectLatest { uiState ->
                    uiState.fold(
                        onLoading = {
                            // TODO Evgeny Логирование лучше делать не через Log.d и прочие,
                            // А подключить либу: Timber.
                            Log.d("Retrofit", "NbaFragment: Loading")
                        },
                        onSuccess = { list ->
                            if (list.isNullOrEmpty()) {
                                Log.d("Retrofit", "NbaFragment: Empty")
                            } else {
                                nbaPlayersListAdapter.submitList(
                                    NbaPlayerUiToListItemMapper().invoke(
                                        list
                                    )
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

    private fun initNbaPlayersRecyclerView() {
        nbaPlayersListAdapter = NbaPlayersListAdapter(this@NbaFragment)
        binding.rvNbaPlayers.adapter = nbaPlayersListAdapter
//        TODO какая есть альтернатива использования четырых! let?
        context?.let { context ->
            ContextCompat.getDrawable(context, R.drawable.divider_drawable)?.let { drawable ->
                binding.rvNbaPlayers.context?.let { context ->
                    CustomViewTypeItemDecoration(context, DividerItemDecoration.VERTICAL, drawable)
                }
            }?.let { customViewTypeItemDecoration ->
                binding.rvNbaPlayers.addItemDecoration(customViewTypeItemDecoration)
            }
        }
    }

    override fun onClickedNbaPlayer(nbaPlayerId: Int) {
        val bundle = Bundle()
        bundle.putInt(getString(R.string.arg_nba_player_id), nbaPlayerId)
        findNavController().navigate(R.id.action_nbaFragment_to_nbaDetailsFragment, bundle)
    }
}