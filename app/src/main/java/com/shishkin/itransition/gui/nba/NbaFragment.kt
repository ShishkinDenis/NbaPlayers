package com.shishkin.itransition.gui.nba

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.shishkin.itransition.R
import com.shishkin.itransition.databinding.FragmentNbaBinding
import com.shishkin.itransition.gui.nba.mappers.NbaPlayerUiToListItemMapper
import com.shishkin.itransition.gui.utils.CustomViewTypeItemDecoration
import dagger.android.support.DaggerFragment
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

private const val NBA_PLAYERS_LIST_TAG = "NbaPLayersList"

class NbaFragment : DaggerFragment(), NbaPlayerItemListener {

    @Inject
    lateinit var viewModelFactory: NbaViewModelFactory

    private val viewModel: NbaViewModel by viewModels { viewModelFactory }

    @Inject
    lateinit var nbaPlayerUiToListItemMapper: NbaPlayerUiToListItemMapper
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
        initNbaPlayersRecyclerView()
        lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.playersState.collectLatest { uiState ->
                    uiState.fold(
                        onLoading = {
                            Timber.tag(NBA_PLAYERS_LIST_TAG)
                                .d(getString(R.string.nba_fragment_loading))
                        },
                        onSuccess = { list ->
                            if (list.isNullOrEmpty()) {
                                Timber.tag(NBA_PLAYERS_LIST_TAG)
                                    .d(getString(R.string.nba_fragment_empty))
                            } else {
                                nbaPlayersListAdapter.submitList(
                                    nbaPlayerUiToListItemMapper.invoke(list)
                                )
                            }
                        },
                        onError = { _, message ->
                            Timber.tag(NBA_PLAYERS_LIST_TAG)
                                .e(getString(R.string.nba_fragment_error), message)
                        }
                    )
                }
            }
        }

        observeNbaPlayersList()
    }

    private fun initNbaPlayersRecyclerView() {
        nbaPlayersListAdapter = NbaPlayersListAdapter(this@NbaFragment)
        binding.rvNbaPlayers.adapter = nbaPlayersListAdapter
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

    private fun observeNbaPlayersList() {
        viewModel.nbaPlayersStateRX
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError { }
            .subscribe { list ->
                list.fold(
                    onSuccess = {
                        Timber.tag("RX").d(it?.get(9)?.firstName)
                    },
                    onFailure = {
                    }
                )
            }
    }
}